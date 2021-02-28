package com.ieseuropa.spring.service

import com.ieseuropa.spring.repository.FamilyRepository
import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.*
import com.ieseuropa.spring.service.tool.MockTool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class FamilyService {

    @Autowired lateinit var familyRepository: FamilyRepository
    @Autowired lateinit var documentService: DocumentService
    @Autowired lateinit var mockTool: MockTool


    fun init() {
        if (familyRepository.count() <= 0) {
            create("Prueba1", "lorem", mockTool.multipartFileImage(), null)
            create("Prueba2", null, null, "https://www.youtube.com/watch?v=YuZ6g77623I")
        }
    }

    fun create(title: String, body: String?, bannerFile: MultipartFile?, url: String?): Family {
        if (title.isBlank()) throw IllegalArgumentException()
        val family = Family(title = title)

        body?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            family.body = it
        }

        bannerFile?.let {
            val banner = documentService.create(bannerFile, Document.Type.IMAGE, Family::class.java.simpleName, null)
            family.banner = banner
        }

        url?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            family.url = it
        }


        return familyRepository.save(family)
    }

    fun update(id: Long, title: String?, body: String?, bannerFile: MultipartFile?, url: String?): Family {
        val family = findById(id)

        title?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            family.title = it
        }
        body?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            family.body = it
        }

        bannerFile?.let {
            val banner = documentService.create(bannerFile, Document.Type.IMAGE, Family::class.java.simpleName, null)
            family.banner = banner
        }

        url?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            family.url = it
        }

        return familyRepository.save(family)
    }

    fun addImage(id: Long, imageFile: MultipartFile): Family {
        val family = findById(id)
        family.images.add(
            documentService.create(imageFile, Document.Type.IMAGE, DepartmentContent::class.java.simpleName, null)
        )
        return familyRepository.save(family)
    }

    fun removeImage(id: Long, imageId: Long): Family {
        val family = findById(id)
        val document = documentService.findById(imageId)
        family.images.remove(document)
        documentService.delete(imageId)
        return familyRepository.save(family)
    }

    fun findById(id: Long): Family {
        if (!familyRepository.existsById(id)) {
            throw NotFoundException()
        }
        return familyRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!familyRepository.existsById(id)) {
            throw NotFoundException()
        }
        val family = findById(id)
        family.banner?.let { documentService.delete(it.id!!) }
        familyRepository.deleteById(id)
    }

    fun findAll(): List<Family> {
        return familyRepository.findAllByOrderByIdDesc()
    }
}