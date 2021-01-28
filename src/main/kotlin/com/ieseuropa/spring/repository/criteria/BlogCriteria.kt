package com.ieseuropa.spring.repository.criteria

import com.ieseuropa.spring.entity.Blog
import com.ieseuropa.spring.entity.BlogLabel_
import com.ieseuropa.spring.entity.Blog_
import com.ieseuropa.spring.service.tool.CriteriaTool
import org.springframework.data.domain.Page
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.Path

@Repository
class BlogCriteria {

    @PersistenceContext lateinit var entityManager: EntityManager


    fun findFilterPageable(page: Int, size: Int, search: String?, labelId: Long?): Page<Blog> {
        val cb = entityManager.criteriaBuilder
        val q = cb.createQuery(Blog::class.java)
        val blog = q.from(Blog::class.java)

        val b: Path<Set<String>> = blog.get(Blog_.LABEL)
        val label: Path<Set<String>> = b.get(BlogLabel_.ID)

        labelId?.let {
            q.select(blog).where(cb.equal(label, labelId))
        } ?: run {
            q.select(blog).where()
        }

        return CriteriaTool.page(entityManager, q, page, size)
    }
}