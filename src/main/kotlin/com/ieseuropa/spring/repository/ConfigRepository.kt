package com.ieseuropa.spring.repository

import com.ieseuropa.spring.entity.Config
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConfigRepository : JpaRepository<Config, Long> {

    fun findByKeyOrderByLocationAsc(key: String): List<Config>

}