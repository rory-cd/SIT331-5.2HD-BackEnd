package com.rorycd.artgallery.models.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.rorycd.artgallery.models.ArtworkStatus
import com.rorycd.artgallery.models.Dimensions
import com.rorycd.artgallery.models.Region
import kotlin.String

data class UpdateArtworkRequest(
    val title: String?,
    val artistId: String?,
    val description: String?,
    val exhibitionId: String?,
    val imageUrl: String?,
    val thumbnailUrl: String?,
    val framed: Boolean?,
    val status: ArtworkStatus?,
    val year: Int?,
    val region: Region?,
    val dimensions: Dimensions?,
    val medium: String?,
    val price: Double?
) {
    @JsonIgnore
    fun isEmpty(): Boolean {
        return listOf(
            title,
            artistId,
            description,
            exhibitionId,
            imageUrl,
            thumbnailUrl,
            framed,
            status,
            year,
            region,
            dimensions,
            medium,
            price
        ).all { it == null }
    }
}
