package com.ieseuropa.spring.repository

import com.ieseuropa.spring.entity.TeacherContent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TeacherContentRepository : JpaRepository<TeacherContent, Long> {

    fun findByOrderByLocationAsc(): List<TeacherContent>

}