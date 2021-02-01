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


    fun init() {
        if (teacherContentRepository.count() <= 0) {
            println("TeacherContentService init()")
            create(
                    TeacherContent(
                            fullName = "Silvio Franco",
                            email = "silviofrancoxa8@gmail.com",
                            position = "Jefe de departamento",
                            subjects = "Filosofia",
                            schedule = "de 10AM a 1PM",
                            location = 2
                    ), 1
            )
            create(
                    TeacherContent(
                            fullName = "Celmy Guzman",
                            email = "celmy@gmail.com",
                            position = "Profesora",
                            subjects = "Filosofia",
                            schedule = "de 10AM a 1PM"

                    ), 1
            )
            create(
                    TeacherContent(
                            fullName = "Jose Bozo",
                            email = "jose@gmail.com",
                            position = "Director",
                            subjects = "Filosofia",
                            schedule = "de 10AM a 1PM",
                            location = 1
                    ), 1
            )
        }
    }

    fun create(teacher: TeacherContent, departmentId: Long): TeacherContent {
        val department = departmentContentService.findById(departmentId)

        teacher.id = null
        if (teacher.location == null) teacher.location = 1000
        teacher.department = department

        return teacherContentRepository.save(teacher)
    }

    fun update(id: Long, request: TeacherContent): TeacherContent {
        if (teacherContentRepository.existsById(id)) {
            throw NotFoundException()
        }

        val teacher = teacherContentRepository.getOne(id)


        request.fullName?.let { teacher.fullName = it }
        request.email?.let { teacher.email = it }
        request.position?.let { teacher.position = it }
        request.subjects?.let { teacher.subjects = it }
        request.schedule?.let { teacher.schedule = it }
        request.location?.let {
            teacher.location = it
        } ?: run {
            teacher.location = 1000
        }

        return teacherContentRepository.save(teacher)
    }

    fun delete(id: Long) {
        if (!teacherContentRepository.existsById(id)) {
            throw NotFoundException()
        }
        teacherContentRepository.deleteById(id)
    }

    fun findAll(): List<TeacherContent> {
        return teacherContentRepository.findByOrderByLocationAsc()
    }
}