package com.ieseuropa.spring.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class ResourceCategory(
        @Id @GeneratedValue
        var id: Long? = null,
        var category: String? = null,
        @OneToMany(mappedBy = "resourceCategory")
        var resources: List<Resource> = listOf()
)