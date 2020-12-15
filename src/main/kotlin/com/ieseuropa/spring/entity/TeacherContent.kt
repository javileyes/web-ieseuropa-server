package com.ieseuropa.spring.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class TeacherContent(
        @Id @GeneratedValue
        var id: Long? = null,
        var fullName: String? = null,
        var email: String? = null,
        var position: String? = null,
        var subject: String? = null,
        var shedules: String? = null,
        @OneToOne
        var department: DepartmentContent? = null
)