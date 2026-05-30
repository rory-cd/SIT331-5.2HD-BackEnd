package com.rorycd.artgallery.service

import com.rorycd.artgallery.models.dto.request.CreateArtistRequest
import com.rorycd.artgallery.models.dto.request.UpdateArtistRequest
import com.rorycd.artgallery.models.dto.response.ArtistResponse

interface ArtistService {
    fun getAllArtists(): List<ArtistResponse>
    fun getArtist(id: String): ArtistResponse
    fun createArtist(request: CreateArtistRequest): ArtistResponse
    fun updateArtist(id: String, request: UpdateArtistRequest)
    fun deleteArtist(id: String)
}
