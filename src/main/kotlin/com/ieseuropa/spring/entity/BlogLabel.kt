package com.ieseuropa.spring.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class BlogLabel(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        @OneToOne
        var image: Document? = null,
        @JsonIgnore
        @OneToMany(mappedBy = "label")
        var blog: List<Blog> = listOf()
)