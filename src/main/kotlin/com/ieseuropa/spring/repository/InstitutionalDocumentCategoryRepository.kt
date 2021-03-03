package com.ieseuropa.spring.repository

import com.ieseuropa.spring.entity.InstitutionalDocumentCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InstitutionalDocumentCategoryRepository : JpaRepository<InstitutionalDocumentCategory, Long> {
}