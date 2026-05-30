package com.rorycd.artgallery.models.dto.request

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.Instant

data class UpdateExhibitionRequest (
    val title: String?,
    val description: String?,
    val startDate: Instant?,
    val endDate: Instant?,
    val venue: String?,
    val coverImageUrl: String?,
) {
    @JsonIgnore
    fun isEmpty(): Boolean {
        return listOf(
            title,
            description,
            startDate,
            endDate,
            venue,
            coverImageUrl
        ).all { it == null }
    }
}
