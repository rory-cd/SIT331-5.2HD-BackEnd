package com.rorycd.artgallery.exceptions

import com.rorycd.artgallery.models.dto.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(ex: ResourceNotFoundException): ResponseEntity<ErrorResponse> {

        val error = ErrorResponse(
            ex.message ?: "Resource not found",
            "RESOURCE_NOT_FOUND"
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(ex: BadRequestException): ResponseEntity<ErrorResponse> {

        val error = ErrorResponse(
            ex.message ?: "Bad request",
            "BAD_REQUEST"
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error)
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException): ResponseEntity<ErrorResponse> {

        val error = ErrorResponse(
            ex.message ?: "Validation error",
            "VALIDATION_ERROR"
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error)
    }
}
