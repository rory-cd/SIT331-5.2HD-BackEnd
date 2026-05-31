package com.rorycd.artgallery.persistence

import com.rorycd.artgallery.models.Artwork
import com.rorycd.artgallery.models.ArtworkFilter

interface ArtworkRepository {
    fun isValidId(id: String): Boolean
    fun getAll(): List<Artwork>
    fun find(filter: ArtworkFilter): List<Artwork>
    fun getByIdOrNull(id: String): Artwork?
    fun existsById(id: String): Boolean
    fun deleteById(id: String)
    fun add(artwork: Artwork): Artwork
    fun count(): Long
}
