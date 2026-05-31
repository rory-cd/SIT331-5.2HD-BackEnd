package com.rorycd.artgallery.models.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant

data class CreateExhibitionRequest (
    @Schema(description = "Title of exhibition", example = "Dreamtime")
    val title: String,
    @Schema(description = "Description of exhibition", example = "A collection of art by local Aboriginal artists.")
    val description: String,
    @Schema(description = "Time and date the exhibition starts", example = "2026-01-15T09:30:00Z")
    val startDate: Instant,
    @Schema(description = "Time and date the exhibition ends", example = "2026-03-15T18:30:00Z")
    val endDate: Instant,
    @Schema(description = "Venue exhibition is located at", example = "National Gallery")
    val venue: String,
    @Schema(description = "Url of the exhibition cover image", example = "https://website.com/images/dreamtime-exhibition-cover.jpg")
    val coverImageUrl: String
)
