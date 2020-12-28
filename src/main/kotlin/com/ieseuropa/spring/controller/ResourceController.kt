package com.ieseuropa.spring.controller

import com.ieseuropa.spring.entity.Resource
import com.ieseuropa.spring.service.ResourceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class ResourceController {

    @Autowired lateinit var resourceService: ResourceService


    @PostMapping("/api/resource")
    fun postResource(
            @RequestParam documentFile: MultipartFile,
            @RequestParam title: String,
            @RequestParam(required = false) resourceCategoryId: Long?,
            @RequestParam(required = false) departmentId: Long?
    ): ResponseEntity<Resource> {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                resourceService.create(documentFile, title, resourceCategoryId, departmentId)
        )
    }

    @PostMapping("/api/blog/{blogId}/resource")
    fun postBlogResource(
            @RequestParam imageFile: MultipartFile,
            @RequestParam title: String,
            @PathVariable blogId: Long
    ): ResponseEntity<Resource> {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                resourceService.createImage(imageFile, title, blogId)
        )
    }

    @PatchMapping("/api/resource/{id}")
    fun patchResource(
            @PathVariable id: Long,
            @RequestParam(required = false) documentFile: MultipartFile?,
            @RequestParam(required = false) title: String?,
            @RequestParam(required = false) resourceCategoryId: Long?,
            @RequestParam(required = false) departmentId: Long?
    ): ResponseEntity<Resource> {
        return ResponseEntity.status(HttpStatus.OK).body(
                resourceService.update(id, documentFile, title, resourceCategoryId, departmentId)
        )
    }

    @DeleteMapping("/api/resource/{id}")
    fun deleteResource(@PathVariable id: Long): ResponseEntity<Void> {
        resourceService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/resource")
    fun getResources(): ResponseEntity<List<Resource>> {
        return ResponseEntity.status(HttpStatus.OK).body(resourceService.findAll())
    }

}