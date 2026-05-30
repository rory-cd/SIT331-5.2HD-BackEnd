package com.rorycd.artgallery.controllers

import com.rorycd.artgallery.models.dto.CreateArtworkRequest
import com.rorycd.artgallery.models.dto.FilterArtworkRequest
import com.rorycd.artgallery.models.dto.UpdateArtworkRequest
import com.rorycd.artgallery.models.response.ArtworkResponse
import com.rorycd.artgallery.service.ArtworkService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI


@RestController
@RequestMapping("/v1/artworks")
class ArtworkController(
    private val artworkService: ArtworkService
) {
    // Get artworks with filter
    @GetMapping
    fun getArtworks(@ModelAttribute request: FilterArtworkRequest): ResponseEntity<List<ArtworkResponse>> {
        val artworkResponses = artworkService.getArtworks(request)
        return ResponseEntity.ok(artworkResponses)
    }

    // Get artwork by ID
    @GetMapping("/{id}")
    fun getArtworkById(@PathVariable id: String): ResponseEntity<ArtworkResponse> {
        val artworkResponse = artworkService.getArtwork(id)
        return ResponseEntity.ok(artworkResponse)
    }

    // Create artwork
    @PostMapping
    fun createArtwork(@RequestBody request: CreateArtworkRequest): ResponseEntity<ArtworkResponse> {
        val artworkResponse = artworkService.createArtwork(request)

        // Build location from current request URL
        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(artworkResponse.id)
            .toUri()

        return ResponseEntity.created(location).body(artworkResponse)
    }

    // Update artwork
    @PatchMapping("/{id}")
    fun updateArtwork(@PathVariable id: String, @RequestBody request: UpdateArtworkRequest): ResponseEntity<Void> {
        artworkService.updateArtwork(id, request)
        return ResponseEntity.noContent().build()
    }

    // Delete artwork
    @DeleteMapping("/{id}")
    fun deleteArtwork(@PathVariable id: String): ResponseEntity<Void> {
        artworkService.deleteArtwork(id)
        return ResponseEntity.noContent().build()
    }
}
