package com.rorycd.artgallery.controllers

import com.rorycd.artgallery.models.dto.request.CreateArtistRequest
import com.rorycd.artgallery.models.dto.request.UpdateArtistRequest
import com.rorycd.artgallery.models.dto.response.ArtistResponse
import com.rorycd.artgallery.service.ArtistService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.parameters.RequestBody as OASRequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
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

@Tag(
    name = "Artists",
    description = "Endpoints for performing CRUD operations on artists."
)
@CrossOrigin(origins = ["http://localhost:3000"])
@RestController
@RequestMapping("/artists")
class ArtistController(
    private val artistService: ArtistService
) {
    @GetMapping
    @Operation(
        summary = "Get all artists",
        description = "Retrieves a full, unfiltered list of artists."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Returns a list of all artists.")
    ])
    fun getAllArtists() : ResponseEntity<List<ArtistResponse>> {
        val artistResponses = artistService.getAllArtists()
        return ResponseEntity.ok(artistResponses)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get artist by ID", description = "Retrieves an artist with the specified ID.\n\n### Sample Request:\n\n```http\nGET /api/artists/6a1ac5f12cc171418fddea32\n```")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully returns an artist with a matching ID"),
        ApiResponse(responseCode = "400", description = "The requested ID is not in the correct format", content = [Content()]),
        ApiResponse(responseCode = "404", description = "An artist with a matching ID does not exist", content = [Content()])
    ])
    fun getArtistById(
        @Parameter(description = "Artist ID (24-character hex string)")
        @PathVariable id: String
    ): ResponseEntity<ArtistResponse> {
        val artistResponse = artistService.getArtist(id)
        return ResponseEntity.ok(artistResponse)
    }

    @PostMapping
    @Operation(summary = "Create new artist", description = "Creates a new artist, provided in the request body.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Returns the newly created artist"),
        ApiResponse(responseCode = "400", description = "The requested creation data is not in the correct format", content = [Content()]),
    ])
    fun createArtist(
        @OASRequestBody(description = "Artist details to create")
        @RequestBody request: CreateArtistRequest
    ): ResponseEntity<ArtistResponse> {
        val artistResponse = artistService.createArtist(request)

        // Build location from current request URL
        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(artistResponse.id)
            .toUri()

        return ResponseEntity.created(location).body(artistResponse)
    }

    @PatchMapping("/{id}")
    @Operation(
        summary = "Update an artist",
        description = "Updates the artist with the specified ID."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "No content: Update was successful"),
        ApiResponse(responseCode = "400", description = "The requested ID or update data is not in the correct format", content = [Content()]),
        ApiResponse(responseCode = "404", description = "An artist with a matching ID does not exist", content = [Content()])
    ])
    fun updateArtist(
        @Parameter(description = "Artist ID (24-character hex string)")
        @PathVariable id: String,
        @OASRequestBody(description = "Artist details to update")
        @RequestBody request: UpdateArtistRequest
    ): ResponseEntity<Void> {
        artistService.updateArtist(id, request)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete an artist",
        description = "Deletes the artist with the specified ID."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "No content: Update was successful"),
        ApiResponse(responseCode = "400", description = "The requested ID is not in the correct format", content = [Content()]),
        ApiResponse(responseCode = "404", description = "An artist with a matching ID does not exist", content = [Content()])
    ])
    fun deleteArtist(
        @Parameter(description = "Artist ID (24-character hex string)")
        @PathVariable id: String
    ): ResponseEntity<Void> {
        artistService.deleteArtist(id)
        return ResponseEntity.noContent().build()
    }
}
