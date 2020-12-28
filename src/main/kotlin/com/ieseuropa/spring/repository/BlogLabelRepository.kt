package com.ieseuropa.spring.repository

import com.ieseuropa.spring.entity.BlogLabel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BlogLabelRepository : JpaRepository<BlogLabel, Long> {
}