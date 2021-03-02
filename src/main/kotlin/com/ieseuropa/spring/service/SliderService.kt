package com.ieseuropa.spring.service

import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.Document
import com.ieseuropa.spring.entity.Slider
import com.ieseuropa.spring.repository.SliderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class SliderService {

    @Autowired lateinit var sliderRepository: SliderRepository
    @Autowired lateinit var documentService: DocumentService


    fun init() {
        if (sliderRepository.count() <= 0) {
            TODO("IMPLEMENT THIS")
        }
    }

    fun create(title: String, url: String?, imageFile: MultipartFile, location: Int?): Slider {

        val image = documentService.create(imageFile, Document.Type.IMAGE, Slider::class.java.simpleName, title)

        val slider = Slider(
                title = title,
                image = image
        )

        url?.let { slider.url = it }
        location?.let {
            slider.location = it
        } ?: run {
            slider.location = 1000
        }

        return sliderRepository.save(slider)
    }

    fun findById(id: Long): Slider {
        if (!sliderRepository.existsById(id)) {
            throw NotFoundException()
        }
        return sliderRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!sliderRepository.existsById(id)) {
            throw NotFoundException()
        }
        val slider = findById(id)
        slider.image?.let { documentService.delete(it.id!!) }
        sliderRepository.deleteById(id)
    }

    fun findAll(): List<Slider> {
        return sliderRepository.findByOrderByLocationAsc()
    }
}