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
        @OrderBy("location ASC")
        @OneToMany(mappedBy = "department")
        var teachers: List<TeacherContent> = listOf(),
        @OneToMany
        @JoinTable(
                name = "rel_department_document",
                joinColumns = [JoinColumn(name = "department_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "document_id", referencedColumnName = "id")])
        var documents: MutableList<Document> = mutableListOf()
)