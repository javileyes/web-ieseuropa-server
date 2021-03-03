package com.ieseuropa.spring.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class InstitutionalDocument(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        @JsonIgnore
        @ManyToOne
        var institutionalDocumentCategory: InstitutionalDocumentCategory? = null,
        @OneToOne
        var document: Document? = null
)