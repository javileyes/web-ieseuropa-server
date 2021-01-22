package com.ieseuropa.spring.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class Slider(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        @OneToOne
        var image: Document? = null
)