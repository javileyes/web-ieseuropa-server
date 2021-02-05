package com.ieseuropa.spring.config

import com.ieseuropa.spring.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PopulateConfig {

    @Autowired lateinit var authorityService: AuthorityService
    @Autowired lateinit var userService: UserService
    @Autowired lateinit var resourceCategoryService: ResourceCategoryService
    @Autowired lateinit var blogLabelService: BlogLabelService
    @Autowired lateinit var blogService: BlogService
    @Autowired lateinit var departmentContentService: DepartmentContentService
    @Autowired lateinit var teacherContentService: TeacherContentService


    fun init() {
        authorityService.init()
        userService.init()
//        resourceCategoryService.init()
//        blogLabelService.init()
//        blogService.init()
        departmentContentService.init()
        teacherContentService.init()
    }

}