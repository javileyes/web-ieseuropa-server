package com.ieseuropa.spring.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class TeacherContent(
        @Id @GeneratedValue
        var id: Long? = null,
        var fullName: String? = null,
        var email: String? = null,
        var position: String? = null,
        var subjects: String? = null,
        var schedule: String? = null,
        @JsonIgnore
        @ManyToOne
        var department: DepartmentContent? = null
)