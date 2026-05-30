package com.rorycd.artgallery.service

import com.rorycd.artgallery.exceptions.ResourceNotFoundException
import com.rorycd.artgallery.exceptions.ValidationException
import com.rorycd.artgallery.models.Artist
import com.rorycd.artgallery.models.dto.request.CreateArtistRequest
import com.rorycd.artgallery.models.dto.request.UpdateArtistRequest
import com.rorycd.artgallery.models.dto.response.ArtistResponse
import com.rorycd.artgallery.models.dto.response.toResponse
import com.rorycd.artgallery.persistence.ArtistRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ArtistServiceImpl(
    private val artistRepo: ArtistRepository
) : ArtistService {

    override fun getAllArtists(): List<ArtistResponse> =
        artistRepo.getAll().map { it.toResponse() }

    override fun getArtist(id: String): ArtistResponse {
        // Check valid id format
        if (!artistRepo.isValidId(id)) {
            throw ValidationException("Invalid artist ID format")
        }
        // Get artist
        val artist = artistRepo.getByIdOrNull(id)
            ?: throw ResourceNotFoundException("Artist with ID $id not found")

        return artist.toResponse()
    }

    override fun createArtist(request: CreateArtistRequest): ArtistResponse {

        val artist = Artist(
            firstName = request.firstName,
            lastName = request.lastName,
            biography = request.biography,
            dateOfBirth = request.dateOfBirth,
            born = request.born,
            language = request.language,
            community = request.community,
            profileImageUrl = request.profileImageUrl
        )

        val saved = artistRepo.add(artist)
        return saved.toResponse()
    }

    override fun updateArtist(id: String, request: UpdateArtistRequest
    ) {
        // Check valid id format
        if (!artistRepo.isValidId(id))
            throw ValidationException("Invalid artist ID format")

        // Check request
        if (request.isEmpty())
            throw ValidationException("No update data provided")

        // Get original artist
        val original = artistRepo.getByIdOrNull(id)
            ?: throw ResourceNotFoundException("Artist with ID $id not found")

        val updated = original.copy(
            firstName = request.firstName ?: original.firstName,
            lastName = request.lastName ?: original.lastName,
            biography = request.biography ?: original.biography,
            dateOfBirth = request.dateOfBirth ?: original.dateOfBirth,
            born = request.born ?: original.born,
            language = request.language ?: original.language,
            community = request.community ?: original.community,
            profileImageUrl = request.profileImageUrl ?: original.profileImageUrl,
            modifiedAt = Instant.now()
        )
        artistRepo.add(updated)
    }

    override fun deleteArtist(id: String) {
        // Check valid id format
        if (!artistRepo.isValidId(id)) {
            throw ValidationException("Invalid artist ID format")
        }
        // Check artist exists
        if (!artistRepo.existsById(id)) {
            throw ResourceNotFoundException("Artist with ID $id not found")
        }
        artistRepo.deleteById(id)
    }
}
