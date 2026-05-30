package com.rorycd.artgallery.models.dto.response

import com.rorycd.artgallery.models.Artist
import java.time.Instant
import java.time.LocalDate

data class ArtistResponse (
    val id: String?,
    val firstName: String,
    val lastName: String,
    val biography: String,
    val dateOfBirth: LocalDate?,
    val born: String?,
    val language: String?,
    val community: String?,
    val profileImageUrl: String,
    val createdAt: Instant,
    val modifiedAt: Instant
)

fun Artist.toResponse(): ArtistResponse {
    return ArtistResponse(
        id = id,
        firstName = firstName,
        lastName = lastName,
        biography = biography,
        dateOfBirth = dateOfBirth,
        born = born,
        language = language,
        community = community,
        profileImageUrl = profileImageUrl,
        createdAt = createdAt,
        modifiedAt = modifiedAt
    )
}
