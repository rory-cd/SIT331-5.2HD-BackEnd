package com.rorycd.artgallery.models.dto.response

data class PaginatedResponse<T> (
    val content: List<T>,
    val currentPage: Int,
    val totalPages: Int,
    val pageSize: Int,
    val totalItems: Long
)
