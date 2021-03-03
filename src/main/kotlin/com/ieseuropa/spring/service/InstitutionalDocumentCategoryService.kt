package com.ieseuropa.spring.service

import com.ieseuropa.spring.entity.InstitutionalDocumentCategory
import com.ieseuropa.spring.repository.InstitutionalDocumentCategoryRepository
import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.ResourceCategory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class InstitutionalDocumentCategoryService {

    @Autowired lateinit var institutionalDocumentCategoryRepository: InstitutionalDocumentCategoryRepository


    fun init() {
        if (institutionalDocumentCategoryRepository.count() <= 0) {
            TODO("IMPLEMENT THIS")
        }
    }

    fun create(title: String): InstitutionalDocumentCategory {
        if (title.isBlank()) {
            throw IllegalArgumentException()
        }

        var institutionalDocumentCategory = InstitutionalDocumentCategory()
        institutionalDocumentCategory.title = title

        return institutionalDocumentCategoryRepository.save(institutionalDocumentCategory)
    }

    fun update(id: Long, title: String): InstitutionalDocumentCategory {
        if (title.isBlank()) {
            throw IllegalArgumentException()
        }
        var institutionalDocumentCategory = findById(id)
        institutionalDocumentCategory.title = title
        return institutionalDocumentCategoryRepository.save(institutionalDocumentCategory)
    }

    fun findById(id: Long): InstitutionalDocumentCategory {
        if (!institutionalDocumentCategoryRepository.existsById(id)) {
            throw NotFoundException()
        }
        return institutionalDocumentCategoryRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!institutionalDocumentCategoryRepository.existsById(id)) {
            throw NotFoundException()
        }
        institutionalDocumentCategoryRepository.deleteById(id)
    }

    fun findAll(): List<InstitutionalDocumentCategory> {
        return institutionalDocumentCategoryRepository.findAll()
    }
}