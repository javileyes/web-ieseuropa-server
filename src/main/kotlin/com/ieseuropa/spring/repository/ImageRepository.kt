package com.ieseuropa.spring.repository

import com.ieseuropa.spring.entity.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : JpaRepository<Image, Long> {

    fun findByKey(key: String): List<Image>

}