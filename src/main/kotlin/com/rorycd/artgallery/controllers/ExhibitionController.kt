package com.rorycd.artgallery.controllers

import com.rorycd.artgallery.models.dto.request.CreateExhibitionRequest
import com.rorycd.artgallery.models.dto.request.UpdateExhibitionRequest
import com.rorycd.artgallery.models.dto.response.ExhibitionResponse
import com.rorycd.artgallery.service.ExhibitionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.parameters.RequestBody as OASRequestBody
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@Tag(
    name = "Exhibitions",
    description = "Endpoints for performing CRUD operations on exhibitions."
)
@CrossOrigin(origins = ["http://localhost:3000"])
@RestController
@RequestMapping("/exhibitions")
class ExhibitionController(
    private val exhibitionService: ExhibitionService
) {
    @GetMapping
    @Operation(
        summary = "Get all exhibitions",
        description = "Retrieves a full, unfiltered list of exhibitions."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Returns a list of all exhibitions")
    ])
    fun getAllExhibitions() : ResponseEntity<List<ExhibitionResponse>> {
        val exhibitionResponses = exhibitionService.getAllExhibitions()
        return ResponseEntity.ok(exhibitionResponses)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get exhibition by ID", description = "Retrieves an exhibition with the specified ID.\n\n### Sample Request:\n\n```http\nGET /api/exhibitions/6a1ae6e838cc29f8c1afd2da\n```")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully returns an exhibition with a matching ID"),
        ApiResponse(responseCode = "400", description = "The requested ID is not in the correct format", content = [Content()]),
        ApiResponse(responseCode = "404", description = "An exhibition with a matching ID does not exist", content = [Content()])
    ])
    fun getExhibitionById(
        @Parameter(description = "Exhibition Id (24-character hex string)")
        @PathVariable id: String
    ): ResponseEntity<ExhibitionResponse> {
        val exhibitionResponse = exhibitionService.getExhibition(id)
        return ResponseEntity.ok(exhibitionResponse)
    }

    @PostMapping
    @Operation(summary = "Create new exhibition", description = "Creates a new exhibition, provided in the request body.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Returns the newly created exhibition"),
        ApiResponse(responseCode = "400", description = "The requested creation data is not in the correct format", content = [Content()]),
    ])
    fun createExhibition(
        @OASRequestBody(description = "Exhibition details to create")
        @RequestBody request: CreateExhibitionRequest
    ): ResponseEntity<ExhibitionResponse> {
        val exhibitionResponse = exhibitionService.createExhibition(request)

        // Build location from current request URL
        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(exhibitionResponse.id)
            .toUri()

        return ResponseEntity.created(location).body(exhibitionResponse)
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update an exhibition",
        description = "Updates the exhibition with the specified ID."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "No content: Update was successful"),
        ApiResponse(responseCode = "400", description = "The requested ID or update data is not in the correct format", content = [Content()]),
        ApiResponse(responseCode = "404", description = "An exhibition with a matching ID does not exist", content = [Content()])
    ])
    fun updateExhibition(
        @Parameter(description = "Exhibition ID (24-character hex string)")
        @PathVariable id: String,
        @OASRequestBody(description = "Exhibition details to update")
        @RequestBody request: UpdateExhibitionRequest
    ): ResponseEntity<Void> {
        exhibitionService.updateExhibition(id, request)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete an exhibition",
        description = "Deletes the exhibition with the specified ID."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "No content: Update was successful"),
        ApiResponse(responseCode = "400", description = "The requested ID is not in the correct format", content = [Content()]),
        ApiResponse(responseCode = "404", description = "An exhibition with a matching ID does not exist", content = [Content()])
    ])
    fun deleteExhibition(
        @Parameter(description = "Exhibition ID (24-character hex string)")
        @PathVariable id: String
    ): ResponseEntity<Void> {
        exhibitionService.deleteExhibition(id)
        return ResponseEntity.noContent().build()
    }
}
