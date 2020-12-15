package com.ieseuropa.spring.service

import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.ResourceCategory
import com.ieseuropa.spring.repository.ResourceCategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ResourceCategoryService {

    @Autowired lateinit var resourceCategoryRepository: ResourceCategoryRepository


    fun init() {
        if (resourceCategoryRepository.count() <= 0) {
            create("Category1")
            create("Category2")
            create("Category3")
        }
    }

    fun create(title: String): ResourceCategory {
        if (title.isBlank()) {
            throw IllegalArgumentException()
        }

        var documentCategory = ResourceCategory()
        documentCategory.title = title

        return resourceCategoryRepository.save(documentCategory)
    }

    fun update(id: Long, title: String): ResourceCategory {
        if (title.isBlank()) {
            throw IllegalArgumentException()
        }
        var documentCategory = findById(id)
        documentCategory.title = title
        return resourceCategoryRepository.save(documentCategory)
    }

    fun delete(id: Long) {
        if (!resourceCategoryRepository.existsById(id)) {
            throw NotFoundException()
        }
        resourceCategoryRepository.deleteById(id)
    }

    fun findAll(): List<ResourceCategory> {
        return resourceCategoryRepository.findAll()
    }

    fun findById(id: Long): ResourceCategory {
        if (!resourceCategoryRepository.existsById(id)) {
            throw NotFoundException()
        }
        return resourceCategoryRepository.getOne(id)
    }
}