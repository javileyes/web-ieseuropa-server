package com.ieseuropa.spring.controller

import com.ieseuropa.spring.entity.Config
import com.ieseuropa.spring.entity.Image
import com.ieseuropa.spring.service.ImageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class ImageController {

    @Autowired lateinit var imageService: ImageService


    @PostMapping("/api/image")
    fun postImage(
            @RequestParam title: String,
            @RequestParam key: String,
            @RequestParam imageFile: MultipartFile
    ): ResponseEntity<Image> {
        return ResponseEntity.status(HttpStatus.CREATED).body(imageService.create(title, key, imageFile))
    }

    @DeleteMapping("/api/image/{id}")
    fun deleteImage(@PathVariable id: Long): ResponseEntity<Void> {
        imageService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/image")
    fun getImages(): ResponseEntity<List<Image>> {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.findAll())
    }

    @GetMapping("/public/image/{key}")
    fun getImageByKey(
        @PathVariable key: String
    ): ResponseEntity<List<Image>> {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.findByKey(key))
    }

//    @GetMapping("/public/image/{id}")
//    fun getImage(@PathVariable id: Long): ResponseEntity<Image> {
//        return ResponseEntity.status(HttpStatus.OK).body(imageService.findById(id))
//    }

}