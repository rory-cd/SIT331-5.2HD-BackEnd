package com.rorycd.artgallery.service

import com.rorycd.artgallery.models.dto.CreateArtworkRequest
import com.rorycd.artgallery.models.dto.FilterArtworkRequest
import com.rorycd.artgallery.models.dto.UpdateArtworkRequest
import com.rorycd.artgallery.models.response.ArtworkResponse

interface ArtworkService {
    fun getArtworks(request: FilterArtworkRequest): List<ArtworkResponse>
    fun getArtwork(id: String): ArtworkResponse
    fun createArtwork(request: CreateArtworkRequest): ArtworkResponse
    fun updateArtwork(id: String, request: UpdateArtworkRequest)
    fun deleteArtwork(id: String)
}
