package com.ieseuropa.spring.entity

import javax.persistence.*

@Entity
class Project(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        @Lob
        var body: String? = null,
        @OneToOne
        var banner: Document? = null,
        @OneToMany
        @JoinTable(
                name = "rel_project_document",
                joinColumns = [JoinColumn(name = "project_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "document_id", referencedColumnName = "id")])
        var documents: MutableList<Document> = mutableListOf()
)