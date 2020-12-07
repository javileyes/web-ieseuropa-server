package com.ieseuropa.spring.config

import com.ieseuropa.spring.service.AuthorityService
import com.ieseuropa.spring.service.DocumentCategoryService
import com.ieseuropa.spring.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PopulateConfig {

    @Autowired lateinit var authorityService: AuthorityService
    @Autowired lateinit var userService: UserService
    @Autowired lateinit var documentCategoryService: DocumentCategoryService

    fun init() {
        authorityService.init()
        userService.init()
        documentCategoryService.init()
    }

}