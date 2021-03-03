package com.ieseuropa.spring.controller

import com.ieseuropa.spring.entity.InstitutionalDocumentCategory
import com.ieseuropa.spring.service.InstitutionalDocumentCategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class InstitutionalDocumentCategoryController {

    @Autowired lateinit var institutionalDocumentCategoryService: InstitutionalDocumentCategoryService


    @PostMapping("/api/institutional-document-category")
    fun postInstitutionalDocumentCategory(
            @RequestParam title: String
    ): ResponseEntity<InstitutionalDocumentCategory> {
        return ResponseEntity.status(HttpStatus.CREATED).body(institutionalDocumentCategoryService.create(title))
    }

    @PatchMapping("/api/institutional-document-category/{id}")
    fun pathInstitutionalDocumentCategory(
            @PathVariable id: Long,
            @RequestParam title: String
    ): ResponseEntity<InstitutionalDocumentCategory> {
        return ResponseEntity.status(HttpStatus.OK).body(institutionalDocumentCategoryService.update(id, title))
    }

    @DeleteMapping("/api/institutional-document-category/{id}")
    fun deleteInstitutionalDocumentCategory(@PathVariable id: Long): ResponseEntity<Void> {
        institutionalDocumentCategoryService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/institutional-document-category")
    fun getInstitutionalDocumentCategories(): ResponseEntity<List<InstitutionalDocumentCategory>> {
        return ResponseEntity.status(HttpStatus.OK).body(institutionalDocumentCategoryService.findAll())
    }

    @GetMapping("/public/institutional-document-category/{id}")
    fun getInstitutionalDocumentCategory(@PathVariable id: Long): ResponseEntity<InstitutionalDocumentCategory> {
        return ResponseEntity.status(HttpStatus.OK).body(institutionalDocumentCategoryService.findById(id))
    }

}