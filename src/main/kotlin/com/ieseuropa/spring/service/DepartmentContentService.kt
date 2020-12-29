package com.ieseuropa.spring.service

import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.DepartmentContent
import com.ieseuropa.spring.entity.Document
import com.ieseuropa.spring.repository.DepartmentContentRepository
import com.ieseuropa.spring.service.tool.MockTool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class DepartmentContentService {

    @Autowired lateinit var departmentContentRepository: DepartmentContentRepository
    @Autowired lateinit var documentService: DocumentService
    @Autowired lateinit var mockTool: MockTool


    fun init() {
        if (departmentContentRepository.count() <= 0) {
            println("DepartmentContentService init()")
            create("Filosofia", mockTool.multipartFileImage(), null)
            create("Historia", mockTool.multipartFileImage(), null)
            create("Matematica", mockTool.multipartFileImage(), null)
            create("Dibujo", mockTool.multipartFileImage(), null)
        }
    }

    fun create(title: String, imageFile: MultipartFile, bannerFile: MultipartFile?): DepartmentContent {
        if (title.isBlank()) {
            IllegalArgumentException()
        }

        val image = documentService.create(
                multipartFile = imageFile,
                type = Document.Type.IMAGE,
                tag = DepartmentContent::class.java.simpleName,
                description = null
        )

        var banner: Document? = null
        bannerFile?.let {
            banner = documentService.create(bannerFile, Document.Type.IMAGE, DepartmentContent::class.java.simpleName, null)
        }

        val department = DepartmentContent(
                title = title,
                image = image,
                banner = banner
        )

        return departmentContentRepository.save(department)
    }

    fun update(id: Long, title: String?, imageFile: MultipartFile?, bannerFile: MultipartFile?): DepartmentContent {
        val department = findById(id)

        title?.let { department.title = it }

        if (imageFile != null) {
            val oldImage = department.image
            department.image = documentService.create(imageFile, Document.Type.IMAGE, DepartmentContent::class.java.simpleName, null)
            oldImage?.let { documentService.delete(it.id!!) }
        }

        if (bannerFile != null) {
            val oldBanner = department.banner
            department.banner = documentService.create(bannerFile, Document.Type.IMAGE, DepartmentContent::class.java.simpleName, null)
            oldBanner?.let { documentService.delete(it.id!!) }
        }

        return departmentContentRepository.save(department)
    }

    fun addDocument(id: Long, title: String, documentFile: MultipartFile): DepartmentContent {
        val department = findById(id)
        department.documents.add(
                documentService.create(documentFile, Document.Type.DOCUMENT, DepartmentContent::class.java.simpleName, title)
        )
        return departmentContentRepository.save(department)
    }

    fun removeDocument(id: Long, documentId: Long): DepartmentContent {
        val department = findById(id)
        val document = documentService.findById(documentId)
        department.documents.remove(document)
        documentService.delete(documentId)
        return departmentContentRepository.save(department)
    }

    fun findById(id: Long): DepartmentContent {
        if (!departmentContentRepository.existsById(id)) {
            throw NotFoundException()
        }
        return departmentContentRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!departmentContentRepository.existsById(id)) {
            throw NotFoundException()
        }
        val department = findById(id)
        department.image?.let { documentService.delete(it.id!!) }
        department.banner?.let { documentService.delete(it.id!!) }
        departmentContentRepository.deleteById(id)
    }

    fun findAll(): List<DepartmentContent> {
        return departmentContentRepository.findAll()
    }
}