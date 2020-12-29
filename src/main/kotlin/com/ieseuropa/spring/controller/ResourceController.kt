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


    @PostMapping("/api/resource-category/{resourceCategoryId}/resource")
    fun postResource(
            @PathVariable resourceCategoryId: Long,
            @RequestParam documentFile: MultipartFile,
            @RequestParam title: String
    ): ResponseEntity<Resource> {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                resourceService.create(documentFile, title, resourceCategoryId)
        )
    }

    @PatchMapping("/api/resource-category/{resourceCategoryId}/resource/{id}")
    fun patchResource(
            @PathVariable id: Long,
            @PathVariable resourceCategoryId: Long,
            @RequestParam(required = false) documentFile: MultipartFile?,
            @RequestParam(required = false) title: String?
    ): ResponseEntity<Resource> {
        return ResponseEntity.status(HttpStatus.OK).body(
                resourceService.update(id, documentFile, title, resourceCategoryId)
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