package com.rorycd.artgallery.controllers

import com.rorycd.artgallery.models.dto.request.CreateArtistRequest
import com.rorycd.artgallery.models.dto.request.UpdateArtistRequest
import com.rorycd.artgallery.models.dto.response.ArtistResponse
import com.rorycd.artgallery.service.ArtistService
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
@RequestMapping("/artists")
class ArtistController(
    private val artistService: ArtistService
) {
    // Get all artists
    @GetMapping
    fun getAllArtists() : ResponseEntity<List<ArtistResponse>> {
        val artistResponses = artistService.getAllArtists()
        return ResponseEntity.ok(artistResponses)
    }

    // Get artist by ID
    @GetMapping("/{id}")
    fun getArtistById(@PathVariable id: String): ResponseEntity<ArtistResponse> {
        val artistResponse = artistService.getArtist(id)
        return ResponseEntity.ok(artistResponse)
    }

    // Create artist
    @PostMapping
    fun createArtist(@RequestBody request: CreateArtistRequest): ResponseEntity<ArtistResponse> {
        val artistResponse = artistService.createArtist(request)

        // Build location from current request URL
        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(artistResponse.id)
            .toUri()

        return ResponseEntity.created(location).body(artistResponse)
    }

    // Update artist
    @PatchMapping("/{id}")
    fun updateArtist(@PathVariable id: String, @RequestBody request: UpdateArtistRequest): ResponseEntity<Void> {
        artistService.updateArtist(id, request)
        return ResponseEntity.noContent().build()
    }

    // Delete artist
    @DeleteMapping("/{id}")
    fun deleteArtist(@PathVariable id: String): ResponseEntity<Void> {
        artistService.deleteArtist(id)
        return ResponseEntity.noContent().build()
    }
}
