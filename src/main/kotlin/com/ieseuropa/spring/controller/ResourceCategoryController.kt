package com.ieseuropa.spring.controller

import com.ieseuropa.spring.entity.ResourceCategory
import com.ieseuropa.spring.service.ResourceCategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ResourceCategoryController {

    @Autowired lateinit var resourceCategoryService: ResourceCategoryService


    @PostMapping("/api/resource-category")
    fun postDocumentCategory(@RequestParam title: String): ResponseEntity<ResourceCategory> {
        return ResponseEntity.status(HttpStatus.CREATED).body(resourceCategoryService.create(title))
    }

    @PatchMapping("/api/resource-category/{id}")
    fun patchDocumentCategory(@PathVariable id: Long, @RequestParam title: String): ResponseEntity<ResourceCategory> {
        return ResponseEntity.status(HttpStatus.OK).body(resourceCategoryService.update(id, title))
    }

    @DeleteMapping("/api/resource-category/{id}")
    fun deleteDocumentCategory(@PathVariable id: Long): ResponseEntity<Void> {
        resourceCategoryService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/resource-category")
    fun getDocumentService(): ResponseEntity<List<ResourceCategory>> {
        return ResponseEntity.status(HttpStatus.OK).body(resourceCategoryService.findAll())
    }
}