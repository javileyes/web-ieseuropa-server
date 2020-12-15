package com.ieseuropa.spring.controller

import com.ieseuropa.spring.entity.TeacherContent
import com.ieseuropa.spring.service.TeacherContentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TeacherContentController {

    @Autowired lateinit var teacherContentService: TeacherContentService
    @Autowired lateinit var departmentService: TeacherContentService

    @PostMapping("/api/teacher")
    fun postTeacher(
            @RequestBody teacher: TeacherContent,
            @RequestParam departmentId: Long
    ): ResponseEntity<TeacherContent> {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherContentService.create(teacher, departmentId))
    }

    @PatchMapping("/api/teacher/{id}")
    fun pathTeacher(
            @PathVariable id: Long,
            @RequestBody request: TeacherContent,
            @RequestParam(required = false) departmentId: Long?
    ): ResponseEntity<TeacherContent> {
        return ResponseEntity.status(HttpStatus.OK).body(teacherContentService.update(id, request, departmentId))
    }

    @DeleteMapping("/api/teacher/{id}")
    fun deleteTeacher(@PathVariable id: Long): ResponseEntity<Void> {
        teacherContentService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/teacher")
    fun getTeachers(): ResponseEntity<List<TeacherContent>> {
        return ResponseEntity.status(HttpStatus.OK).body(teacherContentService.findAll())
    }

}