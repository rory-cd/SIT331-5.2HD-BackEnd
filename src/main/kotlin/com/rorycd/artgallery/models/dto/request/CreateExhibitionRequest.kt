package com.rorycd.artgallery.models.dto.request

import java.time.Instant

data class CreateExhibitionRequest (
    val title: String,
    val description: String,
    val startDate: Instant,
    val endDate: Instant,
    val venue: String,
    val coverImageUrl: String
)
