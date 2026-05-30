package com.rorycd.artgallery.models.dto.request

import com.rorycd.artgallery.models.ArtworkStatus
import com.rorycd.artgallery.models.Dimensions
import com.rorycd.artgallery.models.Region

data class CreateArtworkRequest(
    val title: String,
    val artistId: String,
    val description: String,
    val exhibitionId: String? = null,
    val imageUrl: String,
    val thumbnailUrl: String,
    val framed: Boolean? = null,
    val status: ArtworkStatus,
    val year: Int? = null,
    val region: Region,
    val dimensions: Dimensions,
    val medium: String,
    val price: Double,
)
