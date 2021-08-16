package com.ieseuropa.spring.service

import com.ieseuropa.spring.entity.Project
import com.ieseuropa.spring.repository.ProjectRepository
import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.Document
import com.ieseuropa.spring.service.tool.MockTool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class ProjectService {

    @Autowired lateinit var projectRepository: ProjectRepository
    @Autowired lateinit var documentService: DocumentService
    @Autowired lateinit var mockTool: MockTool


    fun init() {
        if (projectRepository.count() == 0L) {
            create("Prueba1", "lorem", mockTool.multipartFileImage(), null)
            create("Prueba2", "lorem", mockTool.multipartFileImage(), null)
        }
    }

    fun create(title: String, body: String, bannerFile: MultipartFile, location: Int?): Project {
        val project = Project(
            title = title,
            body = body,
            banner = documentService.create(bannerFile, Document.Type.IMAGE, Project::class.java.simpleName, null)
        )

        location?.let {
            project.location = it
        } ?: run {
            project.location = 1000
        }

        return projectRepository.save(project)
    }

    fun update(id: Long, title: String?, body: String?, bannerFile: MultipartFile?, location: Int?): Project {
        val project = findById(id)

        title?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            project.title = it
        }
        body?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            project.body = it
        }

        location?.let {
            project.location = it
        }

        bannerFile?.let {
            val banner = documentService.create(bannerFile, Document.Type.IMAGE, Project::class.java.simpleName, null)
            project.banner = banner
        }

        return projectRepository.save(project)
    }

    fun addDocument(id: Long, title: String, documentFile: MultipartFile): Project {
        val project = findById(id)
        project.documents.add(
            documentService.create(documentFile, Document.Type.DOCUMENT, Project::class.java.simpleName, title)
        )
        return projectRepository.save(project)
    }

    fun removeDocument(id: Long, documentId: Long): Project {
        val project = findById(id)
        val document = documentService.findById(documentId)
        project.documents.remove(document)
        documentService.delete(documentId)
        return projectRepository.save(project)
    }

    fun findById(id: Long): Project {
        if (!projectRepository.existsById(id)) {
            throw NotFoundException()
        }
        return projectRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!projectRepository.existsById(id)) {
            throw NotFoundException()
        }
        val project = findById(id)
        project.banner?.let { documentService.delete(it.id!!) }
        projectRepository.deleteById(id)
    }

    fun findAll(): List<Project> {
        return projectRepository.findByOrderByLocationAsc()
    }
}