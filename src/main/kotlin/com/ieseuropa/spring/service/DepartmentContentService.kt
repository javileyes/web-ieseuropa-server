package com.ieseuropa.spring.service

import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.DepartmentContent
import com.ieseuropa.spring.entity.Document
import com.ieseuropa.spring.repository.DepartmentContentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class DepartmentContentService {

    @Autowired lateinit var departmentContentRepository: DepartmentContentRepository
    @Autowired lateinit var documentService: DocumentService


    fun create(title: String, documentFile: MultipartFile): DepartmentContent {
        if (title.isBlank()) {
            IllegalArgumentException()
        }

        val document = documentService.create(documentFile, Document.Type.IMAGE, DepartmentContent::class.java.simpleName)

        val department = DepartmentContent(
                title = title,
                image = document
        )

        return departmentContentRepository.save(department)
    }

    fun update(id: Long, title: String, documentFile: MultipartFile?): DepartmentContent {
        val department = findById(id)
        department.title = title

        if (documentFile != null) {
            val oldImage = department.image
            department.image = documentService.create(documentFile, Document.Type.IMAGE, DepartmentContent::class.java.simpleName)
            oldImage?.let { documentService.delete(it.id!!) }
        }

        return departmentContentRepository.save(department)
    }

    fun findById(id: Long): DepartmentContent {
        if (!departmentContentRepository.existsById(id)) {
            NotFoundException()
        }
        return departmentContentRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!departmentContentRepository.existsById(id)) {
            NotFoundException()
        }
        departmentContentRepository.deleteById(id)
    }

    fun findAll(): List<DepartmentContent> {
        return departmentContentRepository.findAll()
    }
}