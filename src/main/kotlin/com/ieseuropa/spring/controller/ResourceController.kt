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
            @RequestParam document: MultipartFile,
            @RequestParam title: String,
            @RequestParam resourceCategoryId: Long
    ): ResponseEntity<Resource> {
        return ResponseEntity.status(HttpStatus.CREATED).body(resourceService.create(document, title, resourceCategoryId))
    }

    @PatchMapping("/api/resource/{id}")
    fun patchResource(
            @PathVariable id: Long,
            @RequestParam(required = false) document: MultipartFile?,
            @RequestParam(required = false) title: String?,
            @RequestParam(required = false) resourceCategoryId: Long?): ResponseEntity<Resource> {
        return ResponseEntity.status(HttpStatus.OK).body(resourceService.update(id, document, title, resourceCategoryId))
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