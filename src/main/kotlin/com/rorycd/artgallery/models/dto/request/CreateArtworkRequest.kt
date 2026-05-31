package com.rorycd.artgallery.models.dto.request

import com.rorycd.artgallery.models.ArtworkStatus
import com.rorycd.artgallery.models.Dimensions
import com.rorycd.artgallery.models.Region
import io.swagger.v3.oas.annotations.media.Schema

data class CreateArtworkRequest(
    @Schema(description = "Artwork title", example = "Uluru")
    val title: String,
    @Schema(description = "ID of the artist who created the artwork (24-character hex string)", example = "6a1ac5f12cc171418fddea32")
    val artistId: String,
    @Schema(description = "Description", example = "Uluru from a new perspective.")
    val description: String,
    @Schema(description = "ID of exhibition in which artwork is featured (24-character hex string)", example = "6a1ae6e838cc29f8c1afd2da")
    val exhibitionId: String? = null,
    @Schema(description = "Url of the artwork image", example = "https://website.com/images/artwork.jpg")
    val imageUrl: String,
    @Schema(description = "Url of the artwork thumbnail image", example = "https://website.com/images/artwork-thumbnail.jpg")
    val thumbnailUrl: String,
    @Schema(description = "Boolean value denoting whether the artwork is framed", example = "true")
    val framed: Boolean? = null,
    @Schema(description = "Status of the artwork", example = "UNLISTED")
    val status: ArtworkStatus,
    @Schema(description = "Year the artwork was produced", example = "2015")
    val year: Int? = null,
    @Schema(description = "Region where the artwork was produced", example = "Alice Springs and Central Desert")
    val region: Region,
    @Schema(description = "Physical dimensions of the artwork")
    val dimensions: Dimensions,
    @Schema(description = "Physical medium of the artwork", example = "Canvas")
    val medium: String,
    @Schema(description = "Price of the artwork", example = "199.0")
    val price: Double,
)
