package com.ieseuropa.spring.service

import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.BlogLabel
import com.ieseuropa.spring.entity.Document
import com.ieseuropa.spring.entity.Resource
import com.ieseuropa.spring.repository.BlogLabelRepository
import com.ieseuropa.spring.service.tool.MockTool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class BlogLabelService {

    @Autowired lateinit var blogLabelRepository: BlogLabelRepository
    @Autowired lateinit var documentService: DocumentService
    @Autowired lateinit var mockTool: MockTool


    fun init() {
        if (blogLabelRepository.count() == 0L) {
            create("example1", mockTool.multipartFileImage())
            create("example2", mockTool.multipartFileImage())
        }
    }

    fun create(title: String, imageFile: MultipartFile): BlogLabel {
        if (title.isBlank()) {
            throw IllegalArgumentException()
        }

        val image = documentService.create(imageFile, Document.Type.IMAGE, Resource::class.java.simpleName, title)

        val blogLabel = BlogLabel(
                title = title,
                image = image
        )

        return blogLabelRepository.save(blogLabel)
    }

    fun findById(id: Long): BlogLabel {
        if (!blogLabelRepository.existsById(id)) {
            throw NotFoundException()
        }
        return blogLabelRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!blogLabelRepository.existsById(id)) {
            throw NotFoundException()
        }
        val label = findById(id)
        label.image?.let { documentService.delete(it.id!!) }
        blogLabelRepository.deleteById(id)
    }

    fun findAll(): List<BlogLabel> {
        return blogLabelRepository.findAll()
    }
}