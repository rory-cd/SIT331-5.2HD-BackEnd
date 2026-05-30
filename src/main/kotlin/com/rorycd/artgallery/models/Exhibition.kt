package com.rorycd.artgallery.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "exhibitions")
data class Exhibition (
    @Id
    val id: String? = null,
    val title: String,
    val description: String,
    val startDate: Instant,
    val endDate: Instant,
    val venue: String,
    val coverImageUrl: String,
    val createdAt: Instant = Instant.now(),
    val modifiedAt: Instant = Instant.now()
)
