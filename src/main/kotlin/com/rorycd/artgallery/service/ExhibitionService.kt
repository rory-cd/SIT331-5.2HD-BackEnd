package com.rorycd.artgallery.service

import com.rorycd.artgallery.models.dto.request.CreateExhibitionRequest
import com.rorycd.artgallery.models.dto.request.UpdateExhibitionRequest
import com.rorycd.artgallery.models.dto.response.ExhibitionResponse

interface ExhibitionService {
    fun getAllExhibitions(): List<ExhibitionResponse>
    fun getExhibition(id: String): ExhibitionResponse
    fun createExhibition(request: CreateExhibitionRequest): ExhibitionResponse
    fun updateExhibition(id: String, request: UpdateExhibitionRequest)
    fun deleteExhibition(id: String)
}
