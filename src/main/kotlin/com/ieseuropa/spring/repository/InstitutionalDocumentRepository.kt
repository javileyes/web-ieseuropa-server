package com.ieseuropa.spring.repository

import com.ieseuropa.spring.entity.InstitutionalDocument
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InstitutionalDocumentRepository : JpaRepository<InstitutionalDocument, Long> {
}