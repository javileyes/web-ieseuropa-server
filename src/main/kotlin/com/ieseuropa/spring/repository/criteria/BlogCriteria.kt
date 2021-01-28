package com.ieseuropa.spring.repository.criteria

import com.ieseuropa.spring.entity.Blog
import com.ieseuropa.spring.service.tool.CriteriaTool
import org.springframework.data.domain.Page
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.Path

@Repository
class BlogCriteria {

    @PersistenceContext lateinit var entityManager: EntityManager


    fun findFilterPageable(page: Int, size: Int, search: String?): Page<Blog> {
        val cb = entityManager.criteriaBuilder
        val q = cb.createQuery(Blog::class.java)
        val blog = q.from(Blog::class.java)
        val label: Path<Set<String>> = blog.get("label_id")
        q.select(blog).where(cb.equal(label, 2))
        return CriteriaTool.page(entityManager, q, page, size)
    }
}