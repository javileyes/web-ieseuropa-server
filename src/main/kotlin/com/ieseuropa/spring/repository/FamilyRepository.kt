package com.ieseuropa.spring.repository

import com.ieseuropa.spring.entity.Family
import com.ieseuropa.spring.entity.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FamilyRepository : JpaRepository<Family, Long> {

    fun findAllByOrderByIdDesc(): List<Family>

}