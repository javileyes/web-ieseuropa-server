package com.ieseuropa.spring.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Resource(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        @JsonIgnore
        @ManyToOne
        var resourceCategory: ResourceCategory? = null,
        @JsonIgnore
        @ManyToOne
        var department: DepartmentContent? = null,
        @JsonIgnore
        @ManyToOne
        var blog: Blog? = null,
        @OneToOne
        var document: Document? = null
)