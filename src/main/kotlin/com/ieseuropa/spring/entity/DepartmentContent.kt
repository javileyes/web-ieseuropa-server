package com.ieseuropa.spring.entity

import javax.persistence.*

@Entity
class DepartmentContent(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        @OneToOne
        var image: Document? = null,
        @OneToOne
        var banner: Document? = null,
        @OneToMany(mappedBy = "department")
        var teachers: List<TeacherContent> = listOf(),
        @OneToMany(mappedBy = "department")
        var resources: List<Resource> = listOf()
)