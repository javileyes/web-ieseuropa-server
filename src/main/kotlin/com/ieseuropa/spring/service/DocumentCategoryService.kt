package com.ieseuropa.spring.service

import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.DocumentCategory
import com.ieseuropa.spring.repository.DocumentCategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class DocumentCategoryService {

    @Autowired lateinit var documentCategoryRepository: DocumentCategoryRepository


    fun init() {
        if (documentCategoryRepository.count() <= 0) {
            create("Category1")
            create("Category2")
            create("Category3")
        }
    }

    fun create(name: String): DocumentCategory {
        if (name.isBlank()) {
            IllegalArgumentException()
        }

        var documentCategory = DocumentCategory()
        documentCategory.category = name

        return documentCategoryRepository.save(documentCategory)
    }

    fun update(id: Long, name: String): DocumentCategory {
        if (name.isBlank()) {
            IllegalArgumentException()
        }
        var documentCategory = findById(id)
        documentCategory.category = name
        return documentCategoryRepository.save(documentCategory)
    }

    fun delete(id: Long) {
        if (!documentCategoryRepository.existsById(id)) {
            throw NotFoundException()
        }
        documentCategoryRepository.deleteById(id)
    }

    fun findAll(): MutableList<DocumentCategory> {
        return documentCategoryRepository.findAll()
    }

    fun findById(id: Long): DocumentCategory {
        if (!documentCategoryRepository.existsById(id)) {
            throw NotFoundException()
        }
        return documentCategoryRepository.getOne(id)
    }
}