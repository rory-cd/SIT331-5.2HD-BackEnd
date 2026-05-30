package com.rorycd.artgallery.models.dto.request

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate

data class UpdateArtistRequest (
    val firstName: String?,
    val lastName: String?,
    val biography: String?,
    val dateOfBirth: LocalDate?,
    val born: String?,
    val language: String?,
    val community: String?,
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
