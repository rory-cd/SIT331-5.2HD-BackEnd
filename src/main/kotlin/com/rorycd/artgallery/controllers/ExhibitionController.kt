package com.rorycd.artgallery.controllers

import com.rorycd.artgallery.models.dto.request.CreateExhibitionRequest
import com.rorycd.artgallery.models.dto.request.UpdateExhibitionRequest
import com.rorycd.artgallery.models.dto.response.ExhibitionResponse
import com.rorycd.artgallery.service.ExhibitionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/exhibitions")
class ExhibitionController(
    private val exhibitionService: ExhibitionService
) {
    // Get all exhibitions
    @GetMapping
    fun getAllExhibitions() : ResponseEntity<List<ExhibitionResponse>> {
        val exhibitionResponses = exhibitionService.getAllExhibitions()
        return ResponseEntity.ok(exhibitionResponses)
    }

    // Get exhibition by ID
    @GetMapping("/{id}")
    fun getExhibitionById(@PathVariable id: String): ResponseEntity<ExhibitionResponse> {
        val exhibitionResponse = exhibitionService.getExhibition(id)
        return ResponseEntity.ok(exhibitionResponse)
    }

    // Create exhibition
    @PostMapping
    fun createExhibition(@RequestBody request: CreateExhibitionRequest): ResponseEntity<ExhibitionResponse> {
        val exhibitionResponse = exhibitionService.createExhibition(request)

        // Build location from current request URL
        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(exhibitionResponse.id)
            .toUri()

        return ResponseEntity.created(location).body(exhibitionResponse)
    }

    // Update exhibition
    @PatchMapping("/{id}")
    fun updateExhibition(@PathVariable id: String, @RequestBody request: UpdateExhibitionRequest): ResponseEntity<Void> {
        exhibitionService.updateExhibition(id, request)
        return ResponseEntity.noContent().build()
    }

    // Delete exhibition
    @DeleteMapping("/{id}")
    fun deleteExhibition(@PathVariable id: String): ResponseEntity<Void> {
        exhibitionService.deleteExhibition(id)
        return ResponseEntity.noContent().build()
    }
}
