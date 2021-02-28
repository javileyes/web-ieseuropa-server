package com.ieseuropa.spring.service

import com.ieseuropa.spring.entity.Image
import com.ieseuropa.spring.repository.ImageRepository
import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.Document
import com.ieseuropa.spring.service.tool.MockTool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class ImageService {

    @Autowired lateinit var imageRepository: ImageRepository
    @Autowired lateinit var documentService: DocumentService
    @Autowired lateinit var mockTool: MockTool


    fun init() {
        if (imageRepository.count() <= 0) {
            create("Prueba1", "llave", mockTool.multipartFileImage())
            create("Prueba2", "llave", mockTool.multipartFileImage())
            create("Prueba3", "llave2", mockTool.multipartFileImage())
        }
    }

    fun create(title: String, key: String, imageFile: MultipartFile): Image {
        val image = Image(
            title = title,
            key = key,
            image = documentService.create(imageFile, Document.Type.IMAGE, Image::class.java.simpleName, null)
        )

        return imageRepository.save(image)
    }

    fun findByKey(key: String): List<Image> {
        return imageRepository.findByKeyOrderByIdDesc(key)
    }

    fun findById(id: Long): Image {
        if (!imageRepository.existsById(id)) {
            throw NotFoundException()
        }
        return imageRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!imageRepository.existsById(id)) {
            throw NotFoundException()
        }
        val image = findById(id)
        image.image?.let { documentService.delete(it.id!!) }
        imageRepository.deleteById(id)
    }

    fun findAll(): List<Image> {
        return imageRepository.findAllByOrderByIdDesc()
    }
}