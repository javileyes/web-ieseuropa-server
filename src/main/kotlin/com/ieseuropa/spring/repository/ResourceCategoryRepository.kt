package com.ieseuropa.spring.repository

import com.ieseuropa.spring.entity.ResourceCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ResourceCategoryRepository: JpaRepository<ResourceCategory, Long> {
}