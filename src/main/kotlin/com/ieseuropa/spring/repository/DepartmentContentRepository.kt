package com.ieseuropa.spring.repository

import com.ieseuropa.spring.entity.DepartmentContent
import com.ieseuropa.spring.entity.Slider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DepartmentContentRepository : JpaRepository<DepartmentContent, Long> {
    fun findByOrderByTitleAsc(): List<DepartmentContent>
}
