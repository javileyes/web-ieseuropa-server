package com.ieseuropa.spring.service

import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.BlogLabel
import com.ieseuropa.spring.entity.Document
import com.ieseuropa.spring.entity.Resource
import com.ieseuropa.spring.repository.BlogLabelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class BlogLabelService {

    @Autowired lateinit var blogLabelRepository: BlogLabelRepository
    @Autowired lateinit var documentService: DocumentService


    fun init() {
        if (blogLabelRepository.count() <= 0) {
            TODO("IMPLEMENT THIS")
        }
    }

    fun create(title: String, imageFile: MultipartFile): BlogLabel {
        if (title.isBlank()) {
            throw IllegalArgumentException()
        }

        val image = documentService.create(imageFile, Document.Type.IMAGE, Resource::class.java.simpleName)

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
        blogLabelRepository.deleteById(id)
    }

    fun findAll(): List<BlogLabel> {
        return blogLabelRepository.findAll()
    }
}