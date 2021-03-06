package com.ieseuropa.spring.service

import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.Blog
import com.ieseuropa.spring.entity.DepartmentContent
import com.ieseuropa.spring.entity.Document
import com.ieseuropa.spring.repository.BlogRepository
import com.ieseuropa.spring.repository.criteria.BlogCriteria
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class BlogService {

    @Autowired lateinit var blogRepository: BlogRepository
    @Autowired lateinit var blogLabelService: BlogLabelService
    @Autowired lateinit var documentService: DocumentService
    @Autowired lateinit var blogCriteria: BlogCriteria


    fun init() {
        if (blogRepository.count() == 0L) {
            create("Hola1", "lorem", 1)
            create("Hola2", "lorem", 2)
        }
    }

    fun create(title: String, body: String, labelId: Long): Blog {
        if (title.isBlank() && body.isBlank()) {
            throw IllegalArgumentException()
        }

        val label = blogLabelService.findById(labelId)

        val blog = Blog(
                title = title,
                body = body,
                label = label
        )

        return blogRepository.save(blog)
    }

    fun update(id: Long, title: String?, body: String?, labelId: Long?): Blog {
        val blog = findById(id)

        title?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            blog.title = it
        }
        body?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            blog.body = it
        }
        labelId?.let {
            val label = blogLabelService.findById(it)
            blog.label = label
        }

        return blogRepository.save(blog)
    }

    fun addImage(id: Long, imageFile: MultipartFile): Blog {
        val blog = findById(id)
        blog.images.add(
                documentService.create(imageFile, Document.Type.IMAGE, DepartmentContent::class.java.simpleName, null)
        )
        return blogRepository.save(blog)
    }

    fun removeImage(id: Long, imageId: Long): Blog {
        val blog = findById(id)
        val document = documentService.findById(imageId)
        blog.images.remove(document)
        documentService.delete(imageId)
        return blogRepository.save(blog)
    }

    fun findById(id: Long): Blog {
        if (!blogRepository.existsById(id)) {
            throw NotFoundException()
        }
        return blogRepository.getOne(id)
    }

    fun findFilterPageable(page: Int, size: Int, search: String?, labelId: Long?): Page<Blog> {
        return blogCriteria.findFilterPageable(page, size, search, labelId)
    }

    fun delete(id: Long) {
        if (!blogRepository.existsById(id)) {
            throw NotFoundException()
        }
        blogRepository.deleteById(id)
    }

}