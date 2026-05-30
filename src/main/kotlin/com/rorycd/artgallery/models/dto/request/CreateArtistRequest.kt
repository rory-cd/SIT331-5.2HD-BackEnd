package com.rorycd.artgallery.models.dto.request

import java.time.LocalDate

data class CreateArtistRequest (
    val firstName: String,
    val lastName: String,
    val biography: String,
    val dateOfBirth: LocalDate?,
    val born: String?,
    val language: String?,
    val community: String?,
    val profileImageUrl: String
)
