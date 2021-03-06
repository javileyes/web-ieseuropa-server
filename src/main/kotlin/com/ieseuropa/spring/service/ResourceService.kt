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
    @Autowired lateinit var departmentContentService: DepartmentContentService
    @Autowired lateinit var blogService: BlogService
    @Autowired lateinit var documentService: DocumentService


    fun create(documentFile: MultipartFile, title: String, resourceCategoryId: Long): Resource {
        if (title.isBlank()) throw IllegalArgumentException()

        val document = documentService.create(documentFile, Document.Type.DOCUMENT, Resource::class.java.simpleName, null)

        val resource = Resource(
                title = title,
                document = document
        )

        val resourceCategory = resourceCategoryService.findById(resourceCategoryId)
        resource.resourceCategory = resourceCategory

        return resourceRepository.save(resource)
    }

    fun update(id: Long, documentFile: MultipartFile?, title: String?, resourceCategoryId: Long?): Resource {
        if (!resourceRepository.existsById(id)) {
            throw NotFoundException()
        }

        var resource = resourceRepository.getOne(id)

        title?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            resource.title = it
        }

        resourceCategoryId?.let {
            val resourceCategory = resourceCategoryService.findById(it)
            resource.resourceCategory = resourceCategory
        }

        if (documentFile != null) {
            val oldDocument = resource.document
            resource.document = documentService.create(documentFile, Document.Type.DOCUMENT, Resource::class.java.simpleName, null)
            oldDocument?.let { documentService.delete(it.id!!) }
        }

        return resourceRepository.save(resource)
    }

    fun delete(id: Long) {
        if (!resourceRepository.existsById(id)) {
            throw NotFoundException()
        }
        var resource = resourceRepository.getOne(id)
        resource.document?.let { documentService.delete(it.id!!) }
        resourceRepository.deleteById(id)
    }

    fun findAll(): List<Resource> {
        return resourceRepository.findAll()
    }

}