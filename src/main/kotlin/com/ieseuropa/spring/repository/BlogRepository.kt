package com.ieseuropa.spring.repository

import com.ieseuropa.spring.entity.Blog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BlogRepository : JpaRepository<Blog, Long> {
}