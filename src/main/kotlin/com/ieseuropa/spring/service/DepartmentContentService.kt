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
//    var departments: List<DepartmentContent> = listOf()


    fun init() {
        if (departmentContentRepository.count() <= 0) {
            println("DepartmentContentService init()")
            create("Filosofia", mockTool.multipartFileImage())
            create("Historia", mockTool.multipartFileImage())
            create("Matematica", mockTool.multipartFileImage())
            create("Dibujo", mockTool.multipartFileImage())
        }
    }

    fun create(title: String, imageFile: MultipartFile): DepartmentContent {
        if (title.isBlank()) {
            IllegalArgumentException()
        }

        val image = documentService.create(imageFile, Document.Type.IMAGE, DepartmentContent::class.java.simpleName)

        val department = DepartmentContent(
                title = title,
                image = image
        )

        return departmentContentRepository.save(department)
    }

    fun update(id: Long, title: String?, imageFile: MultipartFile?): DepartmentContent {
        val department = findById(id)

        title?.let { department.title = it }

        if (imageFile != null) {
            val oldImage = department.image
            department.image = documentService.create(imageFile, Document.Type.IMAGE, DepartmentContent::class.java.simpleName)
            oldImage?.let { documentService.delete(it.id!!) }
        }

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
        departmentContentRepository.deleteById(id)
    }

    fun findAll(): List<DepartmentContent> {
        return departmentContentRepository.findAll()
    }
}