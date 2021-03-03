package com.ieseuropa.spring.service

import com.ieseuropa.spring.repository.InstitutionalDocumentRepository
import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.Document
import com.ieseuropa.spring.entity.InstitutionalDocument
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class InstitutionalDocumentService {

    @Autowired lateinit var institutionalDocumentRepository: InstitutionalDocumentRepository
    @Autowired lateinit var institutionalDocumentCategoryService: InstitutionalDocumentCategoryService
    @Autowired lateinit var documentService: DocumentService


    fun init() {
        if (institutionalDocumentRepository.count() <= 0) {
            TODO("IMPLEMENT THIS")
        }
    }

    fun create(documentFile: MultipartFile, title: String, institutionalDocumentCategoryId: Long): InstitutionalDocument {
        if (title.isBlank()) throw IllegalArgumentException()

        val document = documentService.create(documentFile, Document.Type.DOCUMENT, InstitutionalDocument::class.java.simpleName, null)

        val institutionalDocument = InstitutionalDocument(
            title = title,
            document = document
        )

        val institutionalDocumentCategory = institutionalDocumentCategoryService.findById(institutionalDocumentCategoryId)
        institutionalDocument.institutionalDocumentCategory = institutionalDocumentCategory

        return institutionalDocumentRepository.save(institutionalDocument)
    }

    fun update(id: Long, documentFile: MultipartFile?, title: String?, institutionalDocumentCategoryId: Long?): InstitutionalDocument {
        var institutionalDocument = findById(id)

        title?.let {
            if (it.isBlank()) throw IllegalArgumentException()
            institutionalDocument.title = it
        }

        institutionalDocumentCategoryId?.let {
            val institutionalDocumentCategory = institutionalDocumentCategoryService.findById(it)
            institutionalDocument.institutionalDocumentCategory = institutionalDocumentCategory
        }

        if (documentFile != null) {
            val oldDocument = institutionalDocument.document
            institutionalDocument.document = documentService.create(documentFile, Document.Type.DOCUMENT, InstitutionalDocument::class.java.simpleName, null)
            oldDocument?.let { documentService.delete(it.id!!) }
        }

            return institutionalDocumentRepository.save(institutionalDocument)
    }

    fun findById(id: Long): InstitutionalDocument {
        if (!institutionalDocumentRepository.existsById(id)) {
            throw NotFoundException()
        }
        return institutionalDocumentRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!institutionalDocumentRepository.existsById(id)) {
            throw NotFoundException()
        }
        var institutionalDocument = institutionalDocumentRepository.getOne(id)
        institutionalDocument.document?.let { documentService.delete(it.id!!) }
        institutionalDocumentRepository.deleteById(id)
    }

    fun findAll(): List<InstitutionalDocument> {
        return institutionalDocumentRepository.findAll()
    }
}