package com.rorycd.artgallery.models.dto.request

import com.rorycd.artgallery.models.ArtworkStatus
import com.rorycd.artgallery.models.Orientation
import com.rorycd.artgallery.models.Region
import com.rorycd.artgallery.models.dto.ArtworkFilter

data class FilterArtworkRequest (
    val title: String? = null,
    val artistId: String? = null,
    val exhibitionId: String? = null,
    val framed: Boolean? = null,
    val status: ArtworkStatus? = null,
    val year: Int? = null,
    val region: Region? = null,
    val orientation: Orientation? = null,
    val medium: String? = null,
    val maxPrice: Double? = null,
    val pageNumber: Int = 0,
    val pageSize: Int = 20,
    val sortBy: String? = null,
    val sortOrder: String? = null
)

fun FilterArtworkRequest.toDomainFilter(): ArtworkFilter {
    return ArtworkFilter(
        title = title,
        artistId = artistId,
        exhibitionId = exhibitionId,
        framed = framed,
        status = status,
        year = year,
        region = region,
        orientation = orientation,
        medium = medium,
        maxPrice = maxPrice,
        pageNumber = pageNumber,
        pageSize = pageSize,
        sortBy = sortBy,
        sortOrder = sortOrder
    )
}
