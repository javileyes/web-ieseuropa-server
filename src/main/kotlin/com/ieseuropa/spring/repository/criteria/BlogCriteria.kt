package com.ieseuropa.spring.repository.criteria

import com.ieseuropa.spring.entity.Blog
import com.ieseuropa.spring.service.tool.CriteriaTool
import org.springframework.data.domain.Page
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class BlogCriteria {

    @PersistenceContext lateinit var entityManager: EntityManager


    fun findFilterPageable(page: Int, size: Int, search: String?): Page<Blog> {
        val cb = entityManager.criteriaBuilder
        val q = cb.createQuery(Blog::class.java)
        val blog = q.from(Blog::class.java)
        q.select(blog).where()
        return CriteriaTool.page(entityManager, q, page, size)
    }
}