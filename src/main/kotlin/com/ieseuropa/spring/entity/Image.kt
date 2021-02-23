package com.ieseuropa.spring.entity

import javax.persistence.*

@Entity
class Image(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        var key: String?= null,
        @OneToOne
        var image: Document? = null
)