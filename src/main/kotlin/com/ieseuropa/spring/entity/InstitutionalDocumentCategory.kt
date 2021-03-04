package com.ieseuropa.spring.entity

import javax.persistence.*

@Entity
class InstitutionalDocumentCategory(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        @OneToMany(mappedBy = "institutionalDocumentCategory")
        var institutionalDocuments: List<InstitutionalDocument> = listOf()
)