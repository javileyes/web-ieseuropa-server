package com.ieseuropa.spring.service

import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.TeacherContent
import com.ieseuropa.spring.repository.TeacherContentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class TeacherContentService {

    @Autowired lateinit var teacherContentRepository: TeacherContentRepository
    @Autowired lateinit var departmentContentService: DepartmentContentService


    fun create(teacher: TeacherContent, departmentId: Long): TeacherContent {
        val department = departmentContentService.findById(departmentId)

        teacher.id = null
        teacher.department = department

        return teacherContentRepository.save(teacher)
    }

    fun update(id: Long, request: TeacherContent): TeacherContent {
        if (teacherContentRepository.existsById(id)) {
            throw NotFoundException()
        }

        val teacherContent = teacherContentRepository.getOne(id)

        request.fullName?.let { teacherContent.fullName = it }
        request.email?.let { teacherContent.email = it }
        request.position?.let { teacherContent.position = it }
        request.subjects?.let { teacherContent.subjects = it }
        request.shedule?.let { teacherContent.shedule = it }

        return teacherContentRepository.save(teacherContent)
    }

    fun delete(id: Long) {
        if (!teacherContentRepository.existsById(id)) {
            throw NotFoundException()
        }
        teacherContentRepository.deleteById(id)
    }

    fun findAll(): List<TeacherContent> {
        return teacherContentRepository.findAll()
    }
}