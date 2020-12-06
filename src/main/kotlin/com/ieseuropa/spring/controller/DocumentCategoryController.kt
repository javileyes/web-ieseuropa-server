package com.ieseuropa.spring.controller

import com.ieseuropa.spring.entity.DocumentCategory
import com.ieseuropa.spring.service.DocumentCategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class DocumentCategoryController {

    @Autowired lateinit var documentCategoryService: DocumentCategoryService


    @PostMapping("/api/document-category")
    fun postDocumentCategory(@RequestParam name: String): ResponseEntity<DocumentCategory> {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentCategoryService.create(name))
    }

    @PatchMapping("/api/document-category/{id}")
    fun patchDocumentCategory(@PathVariable id: Long, @RequestParam name: String): ResponseEntity<DocumentCategory> {
        return ResponseEntity.status(HttpStatus.OK).body(documentCategoryService.update(id, name))
    }

    @DeleteMapping("/api/document-category/{id}")
    fun deleteDocumentCategory(@PathVariable id: Long): ResponseEntity<Void> {
        documentCategoryService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/api/document-category")
    fun getDocumentService(): ResponseEntity<MutableList<DocumentCategory>> {
        return ResponseEntity.status(HttpStatus.OK).body(documentCategoryService.findAll())
    }
}