package com.ieseuropa.spring.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class DocumentCategory(
        @Id @GeneratedValue
        var id: Long? = null,
        var category: String? = null
//        @OneToMany(mappedBy = "documentCategory")
//        var documents: List<Document> = listOf()
)