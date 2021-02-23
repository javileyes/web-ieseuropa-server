package com.ieseuropa.spring.controller

import com.ieseuropa.spring.entity.Blog
import com.ieseuropa.spring.entity.Family
import com.ieseuropa.spring.service.FamilyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class FamilyController {

    @Autowired lateinit var familyService: FamilyService


    @PostMapping("/api/family")
    fun postFamily(
            @RequestParam title: String,
            @RequestParam(required = false) body: String?,
            @RequestParam(required = false) bannerFile: MultipartFile?,
            @RequestParam(required = false) url: String?
    ): ResponseEntity<Family> {
        return ResponseEntity.status(HttpStatus.CREATED).body(familyService.create(title, body, bannerFile, url))
    }

    @PatchMapping("/api/family/{id}")
    fun patchFamily(
            @PathVariable id: Long,
            @RequestParam(required = false) title: String?,
            @RequestParam(required = false) body: String?,
            @RequestParam(required = false) bannerFile: MultipartFile?,
            @RequestParam(required = false) url: String?
    ): ResponseEntity<Family> {
        return ResponseEntity.status(HttpStatus.OK).body(familyService.update(id, title, body, bannerFile, url))
    }

    @PostMapping("/api/family/{id}/image")
    fun postFamilyImage(
            @PathVariable id: Long,
            @RequestParam imageFile: MultipartFile
    ): ResponseEntity<Family> {
        return ResponseEntity.status(HttpStatus.OK).body(familyService.addImage(id, imageFile))
    }

    @DeleteMapping("/api/family/{id}/image/{imageId}")
    fun deleteFamilyImage(
            @PathVariable id: Long,
            @PathVariable imageId: Long
    ): ResponseEntity<Family> {
        return ResponseEntity.status(HttpStatus.OK).body(familyService.removeImage(id, imageId))
    }

    @DeleteMapping("/api/family/{id}")
    fun deleteFamily(@PathVariable id: Long): ResponseEntity<Void> {
        familyService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/family")
    fun getFamilies(): ResponseEntity<List<Family>> {
        return ResponseEntity.status(HttpStatus.OK).body(familyService.findAll())
    }

    @GetMapping("/public/family/{id}")
    fun getFamily(@PathVariable id: Long): ResponseEntity<Family> {
        return ResponseEntity.status(HttpStatus.OK).body(familyService.findById(id))
    }

}