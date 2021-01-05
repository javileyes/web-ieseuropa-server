package com.ieseuropa.spring.entity

import javax.persistence.*

@Entity
class Blog(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        @Lob
        var body: String? = null,
        @ManyToOne
        var label: BlogLabel? = null,
        var pinned: Boolean? = null,
        @OneToMany
        @JoinTable(
                name = "rel_blog_document",
                joinColumns = [JoinColumn(name = "blog_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "image_id", referencedColumnName = "id")]
        )
        var images: MutableList<Document> = mutableListOf()
) : Auditing()