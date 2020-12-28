package com.ieseuropa.spring.controller

import com.ieseuropa.spring.entity.BlogLabel
import com.ieseuropa.spring.service.BlogLabelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class BlogLabelController {

    @Autowired lateinit var blogLabelService: BlogLabelService


    @PostMapping("/api/blog-label")
    fun postBlogLabel(
            @RequestParam title: String,
            @RequestParam imageFile: MultipartFile
    ): ResponseEntity<BlogLabel> {
        return ResponseEntity.status(HttpStatus.CREATED).body(blogLabelService.create(title, imageFile))
    }

    @DeleteMapping("/api/blog-label/{id}")
    fun deleteBlogLabel(@PathVariable id: Long): ResponseEntity<Void> {
        blogLabelService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/blog-label")
    fun getBlogLabels(): ResponseEntity<List<BlogLabel>> {
        return ResponseEntity.status(HttpStatus.OK).body(blogLabelService.findAll())
    }

    @GetMapping("/public/blog-label/{id}")
    fun getBlogLabel(@PathVariable id: Long): ResponseEntity<BlogLabel> {
        return ResponseEntity.status(HttpStatus.OK).body(blogLabelService.findById(id))
    }

}