package com.ieseuropa.spring.entity

import javax.persistence.*

@Entity
class Family(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        var url: String? = null,
        @Transient
        var hasInternal: Boolean? = null,
        @Lob
        var body: String? = null,
        @OneToOne
        var banner: Document? = null,
        @OneToMany
        @JoinTable(
                name = "rel_family_document",
                joinColumns = [JoinColumn(name = "family_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "image_id", referencedColumnName = "id")]
        )
        var images: MutableList<Document> = mutableListOf()
)