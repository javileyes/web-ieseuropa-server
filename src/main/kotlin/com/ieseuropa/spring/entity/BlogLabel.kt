package com.ieseuropa.spring.entity

import javax.persistence.*

@Entity
class BlogLabel(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        @OneToOne
        var image: Document? = null,
        @OneToMany(mappedBy = "label")
        var blog: List<Blog> = listOf()
)