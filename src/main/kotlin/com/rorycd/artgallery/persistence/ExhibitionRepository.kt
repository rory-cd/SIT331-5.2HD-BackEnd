package com.rorycd.artgallery.persistence

import com.rorycd.artgallery.models.Exhibition

interface ExhibitionRepository {
    fun isValidId(id: String): Boolean
    fun getAll(): List<Exhibition>
    fun getAllByIds(ids: List<String>): List<Exhibition>
    fun getByIdOrNull(id: String): Exhibition?
    fun existsById(id: String): Boolean
    fun deleteById(id: String)
    fun add(exhibition: Exhibition): Exhibition
}
