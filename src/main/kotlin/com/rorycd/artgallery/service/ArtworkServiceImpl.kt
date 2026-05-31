package com.rorycd.artgallery.service

import com.rorycd.artgallery.exceptions.ResourceNotFoundException
import com.rorycd.artgallery.exceptions.ValidationException
import com.rorycd.artgallery.models.Artwork
import com.rorycd.artgallery.models.dto.request.CreateArtworkRequest
import com.rorycd.artgallery.models.dto.request.FilterArtworkRequest
import com.rorycd.artgallery.models.dto.request.UpdateArtworkRequest
import com.rorycd.artgallery.models.dto.request.toDomainFilter
import com.rorycd.artgallery.models.dto.response.ArtworkResponse
import com.rorycd.artgallery.models.dto.response.toResponse
import com.rorycd.artgallery.persistence.ArtistRepository
import com.rorycd.artgallery.persistence.ArtworkRepository
import com.rorycd.artgallery.persistence.ExhibitionRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.time.Instant


@Service
class ArtworkServiceImpl(
    private val artworkRepo: ArtworkRepository,
    private val artistRepo: ArtistRepository,
    private val exhibitionRepo: ExhibitionRepository
) : ArtworkService {

    override fun getArtworks(request: FilterArtworkRequest): List<ArtworkResponse> {
        val artworks = artworkRepo.find(request.toDomainFilter())

        // Get relevant artists
        val artistIds = artworks.map { it.artistId }.distinct()
        val artists = artistRepo.getAllByIds(artistIds).associateBy { it.id }

        // Get relevant exhibitions
        val exhibitionIds = artworks.map { it.artistId }.distinct()
        val exhibitions = exhibitionRepo.getAllByIds(exhibitionIds).associateBy { it.id }

        return artworks.map { artwork ->
            val artist = artists[artwork.artistId]
            val fullName = artist?.firstName + " " + artist?.lastName
            val exhibition = exhibitions[artwork.exhibitionId]
            artwork.toResponse(fullName, exhibition?.title)
        }
    }

    override fun getArtwork(id: String): ArtworkResponse {
        // Check valid id format
        if (!artworkRepo.isValidId(id)) {
            throw ValidationException("Invalid artwork ID format")
        }
        // Get artwork
        val artwork = artworkRepo.getByIdOrNull(id)
            ?: throw ResourceNotFoundException("Artwork with ID $id not found")

        // Get artist and exhibition
        val artist = artistRepo.getByIdOrNull(artwork.artistId)
        val fullName = artist?.firstName + " " + artist?.lastName
        val exhibition =
            if (artwork.exhibitionId != null)
                exhibitionRepo.getByIdOrNull(artwork.exhibitionId)
            else null

        return artwork.toResponse(fullName, exhibition?.title)
    }

    override fun createArtwork(request: CreateArtworkRequest): ArtworkResponse {
        // Check the specified artist exists
        val artistExists = artistRepo.existsById(request.artistId)
        if (!artistExists)
            throw ResourceNotFoundException("Artist with ID ${request.artistId} does not exist")

        // Check any specified exhibition exists
        if (request.exhibitionId != null) {
            val exhibitionExists = exhibitionRepo.existsById(request.exhibitionId)
            if (!exhibitionExists)
                throw ResourceNotFoundException("Exhibition with ID ${request.exhibitionId} does not exist")
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

        // Get artist and exhibition
        val artist = artistRepo.getByIdOrNull(artwork.artistId)
        val fullName = artist?.firstName + " " + artist?.lastName
        val exhibition =
            if (artwork.exhibitionId != null)
                exhibitionRepo.getByIdOrNull(artwork.exhibitionId)
            else null

        val saved = artworkRepo.add(artwork)
        return saved.toResponse(fullName, exhibition?.title)
    }

    override fun updateArtwork(id: String, request: UpdateArtworkRequest) {
        // Check valid id format
        if (!artworkRepo.isValidId(id))
            throw ValidationException("Invalid artwork ID format")

        // Check request
        if (request.isEmpty())
            throw ValidationException("No update data provided")

        // Get original artwork
        val original = artworkRepo.getByIdOrNull(id)
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
        artworkRepo.add(updated)
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
