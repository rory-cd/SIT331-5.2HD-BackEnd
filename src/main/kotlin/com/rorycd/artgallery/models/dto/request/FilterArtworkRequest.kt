package com.rorycd.artgallery.models.dto.request

import com.rorycd.artgallery.models.ArtworkStatus
import com.rorycd.artgallery.models.Orientation
import com.rorycd.artgallery.models.Region
import com.rorycd.artgallery.models.dto.ArtworkFilter
import io.swagger.v3.oas.annotations.media.Schema

data class FilterArtworkRequest (
    @Schema(description = "Artwork title")
    val title: String? = null,
    @Schema(description = "ID of the artist who created the artwork (24-character hex string)")
    val artistId: String? = null,
    @Schema(description = "ID of exhibition in which artwork is featured (24-character hex string)")
    val exhibitionId: String? = null,
    @Schema(description = "Boolean value denoting whether the artwork is framed")
    val framed: Boolean? = null,
    @Schema(description = "Status of the artwork")
    val status: ArtworkStatus? = null,
    @Schema(description = "Year the artwork was produced")
    val year: Int? = null,
    @Schema(description = "Region where the artwork was produced")
    val region: Region? = null,
    @Schema(description = "Orientation of the artwork")
    val orientation: Orientation? = null,
    @Schema(description = "Physical medium of the artwork")
    val medium: String? = null,
    @Schema(description = "Maximum price of the artwork")
    val maxPrice: Double? = null,
    @Schema(description = "Pagination page number of results")
    val pageNumber: Int = 0,
    @Schema(description = "Pagination page size of results")
    val pageSize: Int = 20,
    @Schema(description = "Attribute used to sort results")
    val sortBy: String? = null,
    @Schema(description = "Sort order of results")
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
