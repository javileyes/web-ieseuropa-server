package com.ieseuropa.spring.entity

import javax.persistence.*

@Entity
class DocumentCategory(
        @Id @GeneratedValue
        var id: Long? = null,
        var category: String? = null
)