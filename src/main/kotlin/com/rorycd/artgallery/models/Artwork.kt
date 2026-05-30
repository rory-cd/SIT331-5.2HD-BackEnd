package com.rorycd.artgallery.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "artwork")
data class Artwork (
    @Id
    val id: String? = null,
    val title: String,
    val artistId: String,
    val description: String,
    val exhibitionId: String?,
    val imageUrl: String,
    val thumbnailUrl: String,
    val isFramed: Boolean?,
    val status: ArtworkStatus,
    val year: Int?,
    val region: Region,
    val dimensions: Dimensions,
    val medium: String,
    val price: Double,
    val createdAt: Instant = Instant.now(),
    val modifiedAt: Instant = Instant.now()
) {
    val orientation: Orientation
        get() = when {
            dimensions.width > dimensions.height + TOLERANCE -> Orientation.LANDSCAPE
            dimensions.height > dimensions.width + TOLERANCE -> Orientation.PORTRAIT
            else -> Orientation.SQUARE
        }

    companion object {
        const val TOLERANCE = 5
    }
}

data class Dimensions(
    val width: Int,
    val height: Int,
    val unit: String
)

enum class ArtworkStatus {
    UNLISTED,
    LISTED,
    ARCHIVED
}

enum class Orientation {
    PORTRAIT,
    LANDSCAPE,
    SQUARE
}
