package com.rorycd.artgallery.controllers

import com.rorycd.artgallery.models.dto.request.CreateArtworkRequest
import com.rorycd.artgallery.models.dto.request.FilterArtworkRequest
import com.rorycd.artgallery.models.dto.request.UpdateArtworkRequest
import com.rorycd.artgallery.models.dto.response.ArtworkResponse
import com.rorycd.artgallery.models.dto.response.PaginatedResponse
import com.rorycd.artgallery.service.ArtworkService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.parameters.RequestBody as OASRequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@Tag(
    name = "Artworks",
    description = "Endpoints for managing, filtering, and searching artworks."
)
@RestController
@RequestMapping("/artworks")
class ArtworkController(
    private val artworkService: ArtworkService
) {
    @GetMapping
    @Operation(
        summary = "Get artworks using optional filters",
        description = "Retrieves a list of artworks filtered by path variables."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully returns a list of filtered artwork"),
        ApiResponse(responseCode = "400", description = "Invalid filter parameters", content = [Content()])
    ])
    fun getArtworks(
        @ParameterObject @ModelAttribute request: FilterArtworkRequest
    ): ResponseEntity<PaginatedResponse<ArtworkResponse>> {
        val artworkResponses = artworkService.getArtworks(request)
        return ResponseEntity.ok(artworkResponses)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get artwork by ID", description = "Retrieves artwork with the specified ID.\n\n### Sample Request:\n\n```http\nGET /api/artworks/6a1ad9ebffd85c27909d6ea6\n```")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully returns artwork with a matching ID"),
        ApiResponse(responseCode = "400", description = "The requested ID is not in the correct format", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Artwork with a matching ID does not exist", content = [Content()])
    ])
    fun getArtworkById(
        @Parameter(description = "Artwork ID (24-character hex string)")
        @PathVariable id: String
    ): ResponseEntity<ArtworkResponse> {
        val artworkResponse = artworkService.getArtwork(id)
        return ResponseEntity.ok(artworkResponse)
    }

    @PostMapping
    @Operation(summary = "Create new artwork", description = "Creates new artwork, provided in the request body.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Returns the newly created artwork"),
        ApiResponse(responseCode = "400", description = "The requested creation data is not in the correct format", content = [Content()]),
    ])
    fun createArtwork(
        @OASRequestBody(description = "Artwork details to create")
        @RequestBody request: CreateArtworkRequest
    ): ResponseEntity<ArtworkResponse> {
        val artworkResponse = artworkService.createArtwork(request)

        // Build location from current request URL
        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(artworkResponse.id)
            .toUri()

        return ResponseEntity.created(location).body(artworkResponse)
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update artwork",
        description = "Updates the artwork with the specified ID."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "No content: Update was successful"),
        ApiResponse(responseCode = "400", description = "The requested ID or update data is not in the correct format", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Artwork with a matching ID does not exist", content = [Content()])
    ])
    fun updateArtwork(
        @Parameter(description = "Artwork ID (24-character hex string)")
        @PathVariable id: String,
        @OASRequestBody(description = "Artwork details to update")
        @RequestBody request: UpdateArtworkRequest
    ): ResponseEntity<Void> {
        artworkService.updateArtwork(id, request)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete artwork",
        description = "Deletes the artwork with the specified ID."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "No content: Update was successful"),
        ApiResponse(responseCode = "400", description = "The requested ID is not in the correct format", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Artwork with a matching ID does not exist", content = [Content()])
    ])
    fun deleteArtwork(
        @Parameter(description = "Artwork ID (24-character hex string)")
        @PathVariable id: String
    ): ResponseEntity<Void> {
        artworkService.deleteArtwork(id)
        return ResponseEntity.noContent().build()
    }
}
