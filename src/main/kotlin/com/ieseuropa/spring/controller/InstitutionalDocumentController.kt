package com.ieseuropa.spring.controller

import com.ieseuropa.spring.entity.InstitutionalDocument
import com.ieseuropa.spring.service.InstitutionalDocumentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class InstitutionalDocumentController {

    @Autowired lateinit var institutionalDocumentService: InstitutionalDocumentService


    @PostMapping("/api/institutional-document-category/{institutionalDocumentCategoryId}/institutional-document")
    fun postInstitutionalDocument(
            @RequestParam title: String,
            @RequestParam documentFile: MultipartFile,
            @PathVariable institutionalDocumentCategoryId: Long
    ): ResponseEntity<InstitutionalDocument> {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            institutionalDocumentService.create(documentFile, title, institutionalDocumentCategoryId)
        )
    }

    @PatchMapping("/api/institutional-document-category/{institutionalDocumentCategoryId}/institutional-document/{id}")
    fun pathInstitutionalDocument(
            @PathVariable id: Long,
            @PathVariable institutionalDocumentCategoryId: Long,
            @RequestParam(required = false) title: String?,
            @RequestParam(required = false) documentFile: MultipartFile?
    ): ResponseEntity<InstitutionalDocument> {
        return ResponseEntity.status(HttpStatus.OK).body(
            institutionalDocumentService.update(id, documentFile, title, institutionalDocumentCategoryId)
        )
    }

    @DeleteMapping("/api/institutional-document/{id}")
    fun deleteInstitutionalDocument(@PathVariable id: Long): ResponseEntity<Void> {
        institutionalDocumentService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/institutional-document")
    fun getInstitutionalDocuments(): ResponseEntity<List<InstitutionalDocument>> {
        return ResponseEntity.status(HttpStatus.OK).body(institutionalDocumentService.findAll())
    }

    @GetMapping("/public/institutional-document/{id}")
    fun getInstitutionalDocument(@PathVariable id: Long): ResponseEntity<InstitutionalDocument> {
        return ResponseEntity.status(HttpStatus.OK).body(institutionalDocumentService.findById(id))
    }

}