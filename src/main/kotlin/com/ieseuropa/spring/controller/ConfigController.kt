package com.ieseuropa.spring.controller

import com.ieseuropa.spring.entity.Config
import com.ieseuropa.spring.service.ConfigService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ConfigController {

    @Autowired lateinit var configService: ConfigService


    @PostMapping("/api/config")
    fun postConfig(
            @RequestBody config: Config
    ): ResponseEntity<Config> {
        return ResponseEntity.status(HttpStatus.CREATED).body(configService.create(config))
    }

    @PatchMapping("/api/config/{id}")
    fun pathConfig(
            @PathVariable id: Long,
            @RequestBody request: Config
    ): ResponseEntity<Config> {
        return ResponseEntity.status(HttpStatus.OK).body(configService.update(id, request))
    }

    @DeleteMapping("/api/config/{id}")
    fun deleteConfig(@PathVariable id: Long): ResponseEntity<Void> {
        configService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/config/{key}")
    fun getConfigByKey(
            @PathVariable key: String
    ): ResponseEntity<List<Config>> {
        return ResponseEntity.status(HttpStatus.OK).body(configService.findAllByKey(key))
    }

}