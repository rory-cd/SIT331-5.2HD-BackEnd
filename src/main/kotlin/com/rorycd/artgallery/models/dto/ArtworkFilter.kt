package com.rorycd.artgallery.models.dto

import com.rorycd.artgallery.models.ArtworkStatus
import com.rorycd.artgallery.models.Orientation
import com.rorycd.artgallery.models.Region

data class ArtworkFilter (
    val title: String?,
    val artistId: String?,
    val exhibitionId: String?,
    val framed: Boolean?,
    val status: ArtworkStatus?,
    val year: Int?,
    val region: Region?,
    val orientation: Orientation?,
    val medium: String?,
    val maxPrice: Double?,
    val pageNumber: Int,
    val pageSize: Int,
    val sortBy: String?,
    val sortOrder: String?
)