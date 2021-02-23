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


    @PostMapping("/api/department/{departmentId}/teacher")
    fun postTeacher(
            @PathVariable departmentId: Long,
            @RequestBody teacher: TeacherContent
    ): ResponseEntity<TeacherContent> {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherContentService.create(teacher, departmentId))
    }

    @PatchMapping("/api/teacher/{id}")
    fun patchTeacher(
            @PathVariable id: Long,
            @RequestBody teacher: TeacherContent
    ): ResponseEntity<TeacherContent> {
        return ResponseEntity.status(HttpStatus.OK).body(teacherContentService.update(id, teacher))
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