package com.ieseuropa.spring.repository

import com.ieseuropa.spring.entity.Config
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConfigRepository : JpaRepository<Config, Long> {

    fun existsByKey(key: String): Boolean

    fun findByKey(key: String): List<Config>

}