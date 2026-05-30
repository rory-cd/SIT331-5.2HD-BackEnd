package com.rorycd.artgallery.models.response

import java.time.Instant

data class ErrorResponse(
    val message: String,
    val code: String,
    val timestamp: String = Instant.now().toString()
)
