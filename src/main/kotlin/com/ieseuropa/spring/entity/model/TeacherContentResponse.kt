package com.ieseuropa.spring.entity.model

import com.ieseuropa.spring.entity.TeacherContent

data class TeacherContentResponse(
        var teacher: TeacherContent,
        var departmentId: Long? = null
)