package com.ieseuropa.spring.controller

import com.ieseuropa.spring.entity.Blog
import com.ieseuropa.spring.service.BlogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class BlogController {

    @Autowired lateinit var blogService: BlogService


    @PostMapping("/api/blog-label/{labelId}/blog")
    fun postBlog(
            @PathVariable labelId: Long,
            @RequestParam title: String,
            @RequestParam body: String
    ): ResponseEntity<Blog> {
        return ResponseEntity.status(HttpStatus.CREATED).body(blogService.create(title, body, labelId))
    }

    @PatchMapping("/api/blog-label/{labelId}/blog/{id}")
    fun pathBlog(
            @PathVariable id: Long,
            @PathVariable labelId: Long,
            @RequestParam(required = false) title: String?,
            @RequestParam(required = false) body: String?
    ): ResponseEntity<Blog> {
        return ResponseEntity.status(HttpStatus.OK).body(blogService.update(id, title, body, labelId))
    }

    @DeleteMapping("/api/blog/{id}")
    fun deleteBlog(@PathVariable id: Long): ResponseEntity<Void> {
        blogService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/blog")
    fun getBlogs(): ResponseEntity<List<Blog>> {
        return ResponseEntity.status(HttpStatus.OK).body(blogService.findAll())
    }

    @GetMapping("/public/blog/{id}")
    fun getBlog(@PathVariable id: Long): ResponseEntity<Blog> {
        return ResponseEntity.status(HttpStatus.OK).body(blogService.findById(id))
    }

}