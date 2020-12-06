package com.ieseuropa.spring.repository

import com.ieseuropa.spring.entity.DocumentCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DocumentCategoryRepository: JpaRepository<DocumentCategory, Long> {
}