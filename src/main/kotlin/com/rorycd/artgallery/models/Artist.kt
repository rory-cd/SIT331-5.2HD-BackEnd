package com.rorycd.artgallery.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.LocalDate

@Document(collection = "artists")
data class Artist (
    @Id
    val id: String? = null,
    val firstName: String,
    val lastName: String,
    val biography: String,
    val dateOfBirth: LocalDate?,
    val born: String?,
    val language: String?,
    val community: String?,
    val profileImageUrl: String,
    val createdAt: Instant = Instant.now(),
    val modifiedAt: Instant = Instant.now()
)
