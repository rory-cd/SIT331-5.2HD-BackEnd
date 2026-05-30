package com.rorycd.artgallery.service

import com.rorycd.artgallery.exceptions.ResourceNotFoundException
import com.rorycd.artgallery.exceptions.ValidationException
import com.rorycd.artgallery.models.Exhibition
import com.rorycd.artgallery.models.dto.request.CreateExhibitionRequest
import com.rorycd.artgallery.models.dto.request.UpdateExhibitionRequest
import com.rorycd.artgallery.models.dto.response.ExhibitionResponse
import com.rorycd.artgallery.models.dto.response.toResponse
import com.rorycd.artgallery.persistence.ExhibitionRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ExhibitionServiceImpl(
    private val exhibitionRepo: ExhibitionRepository
) : ExhibitionService {

    override fun getAllExhibitions(): List<ExhibitionResponse> =
        exhibitionRepo.getAll().map { it.toResponse() }

    override fun getExhibition(id: String): ExhibitionResponse {
        // Check valid id format
        if (!exhibitionRepo.isValidId(id)) {
            throw ValidationException("Invalid exhibition ID format")
        }
        // Get Exhibition
        val exhibition = exhibitionRepo.getByIdOrNull(id)
            ?: throw ResourceNotFoundException("Exhibition with ID $id not found")

        return exhibition.toResponse()
    }

    override fun createExhibition(request: CreateExhibitionRequest): ExhibitionResponse {

        val exhibition = Exhibition(
            title = request.title,
            description = request.description,
            startDate = request.startDate,
            endDate = request.endDate,
            venue = request.venue,
            coverImageUrl = request.coverImageUrl
        )

        val saved = exhibitionRepo.add(exhibition)
        return saved.toResponse()
    }

    override fun updateExhibition(id: String, request: UpdateExhibitionRequest
    ) {
        // Check valid id format
        if (!exhibitionRepo.isValidId(id))
            throw ValidationException("Invalid exhibition ID format")

        // Check request
        if (request.isEmpty())
            throw ValidationException("No update data provided")

        // Get original exhibition
        val original = exhibitionRepo.getByIdOrNull(id)
            ?: throw ResourceNotFoundException("Exhibition with ID $id not found")

        val updated = original.copy(
            title = request.title ?: original.title,
            description = request.description ?: original.description,
            startDate = request.startDate ?: original.startDate,
            endDate = request.endDate ?: original.endDate,
            venue = request.venue ?: original.venue,
            coverImageUrl = request.coverImageUrl ?: original.coverImageUrl,
            modifiedAt = Instant.now()
        )
        exhibitionRepo.add(updated)
    }

    override fun deleteExhibition(id: String) {
        // Check valid id format
        if (!exhibitionRepo.isValidId(id)) {
            throw ValidationException("Invalid exhibition ID format")
        }
        // Check Exhibition exists
        if (!exhibitionRepo.existsById(id)) {
            throw ResourceNotFoundException("Exhibition with ID $id not found")
        }
        exhibitionRepo.deleteById(id)
    }
}
