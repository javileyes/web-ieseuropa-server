package com.ieseuropa.spring.controller

import com.ieseuropa.spring.entity.Blog
import com.ieseuropa.spring.service.BlogService
import com.ieseuropa.spring.service.tool.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

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
            @PathVariable(required = false) labelId: Long?,
            @RequestParam(required = false) title: String?,
            @RequestParam(required = false) body: String?
    ): ResponseEntity<Blog> {
        return ResponseEntity.status(HttpStatus.OK).body(blogService.update(id, title, body, labelId))
    }

    @PostMapping("/api/blog/{id}/image")
    fun postBlogImage(
            @PathVariable id: Long,
            @RequestParam imageFile: MultipartFile
    ): ResponseEntity<Blog> {
        return ResponseEntity.status(HttpStatus.OK).body(blogService.addImage(id, imageFile))
    }

    @DeleteMapping("/api/blog/{id}/image/{imageId}")
    fun deleteBlogImage(
            @PathVariable id: Long,
            @PathVariable imageId: Long
    ): ResponseEntity<Blog> {
        return ResponseEntity.status(HttpStatus.OK).body(blogService.removeImage(id, imageId))
    }

    @DeleteMapping("/api/blog/{id}")
    fun deleteBlog(@PathVariable id: Long): ResponseEntity<Void> {
        blogService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/blog")
    fun getBlogs(
            @RequestParam(required = false) search: String?,
            @RequestParam page: Int,
            @RequestParam size: Int,
            @RequestParam(required = false) labelId: Long?
    ): ResponseEntity<List<Blog>> {
        val result = blogService.findFilterPageable(page, size, search, labelId)
        return ResponseEntity.status(HttpStatus.OK)
                .header(Constants.X_TOTAL_COUNT_HEADER, result.totalElements.toString())
                .body(result.content)
    }

    @GetMapping("/public/blog/{id}")
    fun getBlog(@PathVariable id: Long): ResponseEntity<Blog> {
        return ResponseEntity.status(HttpStatus.OK).body(blogService.findById(id))
    }

}