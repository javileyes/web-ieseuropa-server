package com.ieseuropa.spring.controller

import com.ieseuropa.spring.entity.DepartmentContent
import com.ieseuropa.spring.entity.Project
import com.ieseuropa.spring.service.ProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class ProjectController {

    @Autowired lateinit var projectService: ProjectService


    @PostMapping("/api/project")
    fun postProject(
            @RequestParam title: String,
            @RequestParam body: String,
            @RequestParam bannerFile: MultipartFile
    ): ResponseEntity<Project> {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(title, body, bannerFile))
    }

    @PatchMapping("/api/project/{id}")
    fun pathProject(
            @PathVariable id: Long,
            @RequestParam(required = false) title: String?,
            @RequestParam(required = false) body: String?,
            @RequestParam(required = false) bannerFile: MultipartFile?
    ): ResponseEntity<Project> {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.update(id, title, body, bannerFile))
    }

    @PostMapping("/api/project/{id}/document")
    fun postDepartmentDocument(
        @PathVariable id: Long,
        @RequestParam title: String,
        @RequestParam documentFile: MultipartFile
    ): ResponseEntity<Project> {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.addDocument(id, title, documentFile))
    }

    @DeleteMapping("/api/project/{id}/document/{documentId}")
    fun deleteDepartmentDocument(
        @PathVariable id: Long,
        @PathVariable documentId: Long
    ): ResponseEntity<Project> {
        return ResponseEntity.status(HttpStatus.OK).body(
            projectService.removeDocument(id, documentId)
        )
    }

    @DeleteMapping("/api/project/{id}")
    fun deleteProject(@PathVariable id: Long): ResponseEntity<Void> {
        projectService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/project")
    fun getProjects(): ResponseEntity<List<Project>> {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.findAll())
    }

    @GetMapping("/public/project/{id}")
    fun getProject(@PathVariable id: Long): ResponseEntity<Project> {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.findById(id))
    }

}