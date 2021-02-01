package com.ieseuropa.spring.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Config(
        @Id @GeneratedValue
        var id: Long? = null,
        var key: String? = null,
        var value: String? = null,
        var url: String? = null,
        var meta: String? = null,
        var tag: String? = null,
        var location: Int? = null
)