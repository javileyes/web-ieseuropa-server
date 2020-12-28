package com.ieseuropa.spring.service

import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.Blog
import com.ieseuropa.spring.repository.BlogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class BlogService {

    @Autowired lateinit var blogRepository: BlogRepository


    fun init() {
        if (blogRepository.count() <= 0) {
            TODO("IMPLEMENT THIS")
        }
    }

    fun create(title: String, body: String): Blog {
        if (title.isBlank() && body.isBlank()) {
            throw IllegalArgumentException()
        }

        var blog = Blog(
                title = title,
                body = body
        )

        return blogRepository.save(blog)
    }

    fun update(id: Long, title: String?, body: String?): Blog {
        val blog = findById(id)

        title?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            blog.title = it
        }
        body?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            blog.body = it
        }

        return blogRepository.save(blog)
    }

    fun findById(id: Long): Blog {
        if (!blogRepository.existsById(id)) {
            throw NotFoundException()
        }
        return blogRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!blogRepository.existsById(id)) {
            throw NotFoundException()
        }
        blogRepository.deleteById(id)
    }

    fun findAll(): List<Blog> {
        return blogRepository.findAll()
    }
}