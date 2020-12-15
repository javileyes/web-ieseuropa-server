package com.ieseuropa.spring.controller

import com.ieseuropa.spring.entity.DepartmentContent
import com.ieseuropa.spring.service.DepartmentContentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class DepartmentContentController {

    @Autowired lateinit var departmentContentService: DepartmentContentService


    @PostMapping("/api/department")
    fun postDepartment(
            @RequestParam title: String,
            @RequestParam imageFile: MultipartFile
    ): ResponseEntity<DepartmentContent> {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentContentService.create(title, imageFile))
    }

    @PatchMapping("/api/department/{id}")
    fun pathDepartment(
            @PathVariable id: Long,
            @RequestParam(required = false) title: String?,
            @RequestParam(required = false) imageFile: MultipartFile?
    ): ResponseEntity<DepartmentContent> {
        return ResponseEntity.status(HttpStatus.OK).body(departmentContentService.update(id, title, imageFile))
    }

    @DeleteMapping("/api/department/{id}")
    fun deleteDepartment(@PathVariable id: Long): ResponseEntity<Void> {
        departmentContentService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/department")
    fun getDepartments(): ResponseEntity<List<DepartmentContent>> {
        return ResponseEntity.status(HttpStatus.OK).body(departmentContentService.findAll())
    }

    @GetMapping("/public/department/{id}")
    fun getDepartment(@PathVariable id: Long): ResponseEntity<DepartmentContent> {
        return ResponseEntity.status(HttpStatus.OK).body(departmentContentService.findById(id))
    }

}