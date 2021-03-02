package com.ieseuropa.spring.repository

import com.ieseuropa.spring.entity.Slider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SliderRepository : JpaRepository<Slider, Long> {

    fun findByOrderByLocationAsc(): List<Slider>
}