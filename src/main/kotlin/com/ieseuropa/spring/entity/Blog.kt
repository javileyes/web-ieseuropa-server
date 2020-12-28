package com.ieseuropa.spring.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Blog(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        var body: String? = null,
        @OneToMany(mappedBy = "blog")
        var resources: List<Resource> = listOf()
)