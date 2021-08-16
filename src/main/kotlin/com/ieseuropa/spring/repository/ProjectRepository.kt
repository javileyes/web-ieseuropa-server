package com.ieseuropa.spring.repository

import com.ieseuropa.spring.entity.Image
import com.ieseuropa.spring.entity.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository : JpaRepository<Project, Long> {

    fun findByOrderByLocationAsc(): List<Project>

}