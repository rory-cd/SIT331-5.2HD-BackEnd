package com.rorycd.artgallery.models.dto.response

import com.rorycd.artgallery.models.Artwork
import com.rorycd.artgallery.models.ArtworkStatus
import com.rorycd.artgallery.models.Dimensions
import com.rorycd.artgallery.models.Orientation
import com.rorycd.artgallery.models.Region
import java.time.Instant

data class ArtworkResponse (
    val id: String?,
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
    val orientation: Orientation,
    val createdAt: Instant,
    val modifiedAt: Instant
)

fun Artwork.toResponse(): ArtworkResponse {
    return ArtworkResponse(
        id = id,
        title = title,
        artistId = artistId,
        description = description,
        exhibitionId = exhibitionId,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        isFramed = isFramed,
        status = status,
        year = year,
        region = region,
        dimensions = dimensions,
        medium = medium,
        price = price,
        orientation = orientation,
        createdAt = createdAt,
        modifiedAt = modifiedAt
    )
}
