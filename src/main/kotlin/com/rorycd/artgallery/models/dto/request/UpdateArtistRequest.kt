package com.rorycd.artgallery.models.dto.request

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class UpdateArtistRequest (
    @Schema(description = "First name of the artist", example = "John")
    val firstName: String?,
    @Schema(description = "Last name of the artist", example = "Smith")
    val lastName: String?,
    @Schema(description = "Biography of the artist", example = "An internationally renowned artist and two time winner of the Archibald Prize.")
    val biography: String?,
    @Schema(description = "Artist's date of birth", example = "1975-01-15")
    val dateOfBirth: LocalDate?,
    @Schema(description = "Birthplace of the artist", example = "Alice Springs, NT")
    val born: String?,
    @Schema(description = "Language of the artist", example = "Pitjantjatjara")
    val language: String?,
    @Schema(description = "Community to which the artist belongs", example = "Alice Springs, NT")
    val community: String?,
    @Schema(description = "Url of the artist's profile image", example = "https://website.com/images/artist-image.jpg")
    val profileImageUrl: String?
) {
    @JsonIgnore
    fun isEmpty(): Boolean {
        return listOf(
            firstName,
            lastName,
            biography,
            dateOfBirth,
            born,
            language,
            community,
            profileImageUrl
        ).all { it == null }
    }
}
