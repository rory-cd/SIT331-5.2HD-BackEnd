package com.rorycd.artgallery.service

import com.rorycd.artgallery.exceptions.ResourceNotFoundException
import com.rorycd.artgallery.exceptions.ValidationException
import com.rorycd.artgallery.models.Artwork
import com.rorycd.artgallery.models.dto.CreateArtworkRequest
import com.rorycd.artgallery.models.dto.FilterArtworkRequest
import com.rorycd.artgallery.models.dto.UpdateArtworkRequest
import com.rorycd.artgallery.models.response.ArtworkResponse
import com.rorycd.artgallery.models.response.toResponse
import com.rorycd.artgallery.persistence.ArtworkRepository
import com.rorycd.artgallery.persistence.isValidId
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant


@Service
class ArtworkServiceImpl(
    private val artworkRepo: ArtworkRepository,
    private val mongoTemplate: MongoTemplate,
) : ArtworkService {

    override fun getArtworks(request: FilterArtworkRequest): List<ArtworkResponse> {
        val query = Query()

        // Apply filters
        request.title?.let { query.addCriteria(Criteria.where("title").regex(it, "i")) }
        request.artistId?.let { query.addCriteria(Criteria.where("artistId").`is`(it)) }
        request.exhibitionId?.let { query.addCriteria(Criteria.where("exhibitionId").`is`(it)) }
        request.framed?.let { query.addCriteria(Criteria.where("isFramed").`is`(it)) }
        request.status?.let { query.addCriteria(Criteria.where("status").`is`(it)) }
        request.year?.let { query.addCriteria(Criteria.where("year").`is`(it)) }
        request.region?.let { query.addCriteria(Criteria.where("region").`is`(it)) }
        request.orientation?.let { query.addCriteria(Criteria.where("orientation").`is`(it)) }
        request.medium?.let { query.addCriteria(Criteria.where("medium").`is`(it)) }
        request.maxPrice?.let { query.addCriteria(Criteria.where("price").lte(it)) }

        // Sorting direction
        val direction =
            if (request.sortOrder?.lowercase() == "asc") Sort.Direction.ASC
            else Sort.Direction.DESC

        // Pagination defaults
        val pageNumber = request.pageNumber
        val pageSize = (request.pageSize.coerceIn(1, 100)) // Clamp range
        val sortField = request.sortBy ?: "title"

        // Attach pageable to query
        val pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortField))
        query.with(pageable)

        val artworks = mongoTemplate.find(query, Artwork::class.java)
        return artworks.map { it.toResponse() }
    }

    override fun getArtwork(id: String): ArtworkResponse {
        // Check valid id format
        if (!artworkRepo.isValidId(id)) {
            throw ValidationException("Invalid artwork ID format")
        }
        // Get artwork
        val artwork = artworkRepo.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Artwork with ID $id not found")

        return artwork.toResponse()
    }

    override fun createArtwork(request: CreateArtworkRequest): ArtworkResponse {
        // Check request
        if (request.isEmpty()) {
            throw ValidationException("No creation data provided")
        }

        val artwork = Artwork(
            title = request.title,
            artistId = request.artistId,
            description = request.description,
            exhibitionId = request.exhibitionId,
            imageUrl = request.imageUrl,
            thumbnailUrl = request.thumbnailUrl,
            isFramed = request.framed,
            status = request.status,
            year = request.year,
            region = request.region,
            dimensions = request.dimensions,
            medium = request.medium,
            price = request.price
        )

        val saved = artworkRepo.save(artwork)
        return saved.toResponse()
    }

    override fun updateArtwork(id: String, request: UpdateArtworkRequest) {
        // Check valid id format
        if (!artworkRepo.isValidId(id)) {
            throw ValidationException("Invalid artwork ID format")
        }
        // Check request
        if (request.isEmpty()) {
            throw ValidationException("No update data provided")
        }
        // Get original artwork
        val original = artworkRepo.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Artwork with ID $id not found")

        val updated = original.copy(
            title = request.title ?: original.title,
            artistId = request.artistId ?: original.artistId,
            description = request.description ?: original.description,
            exhibitionId = request.exhibitionId ?: original.exhibitionId,
            imageUrl = request.imageUrl ?: original.imageUrl,
            thumbnailUrl = request.thumbnailUrl ?: original.thumbnailUrl,
            isFramed = request.framed ?: original.isFramed,
            status = request.status ?: original.status,
            year = request.year ?: original.year,
            region = request.region ?: original.region,
            dimensions = request.dimensions ?: original.dimensions,
            medium = request.medium ?: original.medium,
            price = request.price ?: original.price,
            modifiedAt = Instant.now()
        )
        artworkRepo.save(updated)
    }

    override fun deleteArtwork(id: String) {
        // Check valid id format
        if (!artworkRepo.isValidId(id)) {
            throw ValidationException("Invalid artwork ID format")
        }
        // Check artwork exists
        if (!artworkRepo.existsById(id)) {
            throw ResourceNotFoundException("Artwork with ID $id not found")
        }
        artworkRepo.deleteById(id)
    }
}
