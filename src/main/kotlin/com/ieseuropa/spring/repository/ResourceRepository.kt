package com.ieseuropa.spring.repository

import com.ieseuropa.spring.entity.Resource
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ResourceRepository: JpaRepository<Resource, Long> {
}