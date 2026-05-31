package com.rorycd.artgallery.persistence

import com.rorycd.artgallery.models.Artist

interface ArtistRepository {
    fun isValidId(id: String): Boolean
    fun getAll(): List<Artist>
    fun getAllByIds(ids: List<String>): List<Artist>
    fun getByIdOrNull(id: String): Artist?
    fun existsById(id: String): Boolean
    fun deleteById(id: String)
    fun add(artist: Artist): Artist
}
