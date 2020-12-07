package com.ieseuropa.spring.service

import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.Document
import com.ieseuropa.spring.entity.Resource
import com.ieseuropa.spring.repository.ResourceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class ResourceService {

    @Autowired lateinit var resourceRepository: ResourceRepository
    @Autowired lateinit var resourceCategoryService: ResourceCategoryService
    @Autowired lateinit var documentService: DocumentService

    fun create(document: MultipartFile, title: String, resourceCategoryId: Long): Resource {
        if (title.isBlank()) {
            IllegalArgumentException()
        }

        val resourceCategory = resourceCategoryService.findById(resourceCategoryId)
        val document = documentService.create(document, Document.Type.DOCUMENT, Resource::class.java.simpleName)

        val resource = Resource(
                title = title,
                resourceCategory = resourceCategory,
                document = document
        )

        return resourceRepository.save(resource)
    }

    fun update(id: Long, document: MultipartFile?, title: String?, resourceCategoryId: Long?): Resource {
        if (!resourceRepository.existsById(id)) {
            NotFoundException()
        }

        var resource = resourceRepository.getOne(id)

        title?.let {
            if (it.isBlank()) {
                IllegalArgumentException()
            }
            resource.title = it
        }

        resourceCategoryId?.let {
            val resourceCategory = resourceCategoryService.findById(it)
            resource.resourceCategory = resourceCategory
        }

        if (document != null) {
            val oldDocument = resource.document
            resource.document = documentService.create(document, Document.Type.DOCUMENT, Resource::class.java.simpleName)
            oldDocument?.let { documentService.delete(it.id!!) }
        }

        return resourceRepository.save(resource)
    }

    fun delete(id: Long) {
        if (!resourceRepository.existsById(id)) {
            NotFoundException()
        }
        resourceRepository.deleteById(id)
    }

    fun findAll(): List<Resource> {
        return resourceRepository.findAll()
    }

}