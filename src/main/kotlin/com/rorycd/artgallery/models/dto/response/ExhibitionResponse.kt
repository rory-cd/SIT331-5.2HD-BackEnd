package com.rorycd.artgallery.models.dto.response

import com.rorycd.artgallery.models.Exhibition
import java.time.Instant

data class ExhibitionResponse (
    val id: String?,
    val title: String,
    val description: String,
    val startDate: Instant,
    val endDate: Instant,
    val venue: String,
    val coverImageUrl: String,
    val createdAt: Instant,
    val modifiedAt: Instant
)

fun Exhibition.toResponse(): ExhibitionResponse {
    return ExhibitionResponse(
        id = id,
        title = title,
        description = description,
        startDate = startDate,
        endDate = endDate,
        venue = venue,
        coverImageUrl = coverImageUrl,
        createdAt = createdAt,
        modifiedAt = modifiedAt
    )
}
