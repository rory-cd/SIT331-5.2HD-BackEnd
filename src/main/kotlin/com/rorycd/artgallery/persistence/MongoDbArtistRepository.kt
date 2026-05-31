package com.rorycd.artgallery.persistence

import com.rorycd.artgallery.models.Artist
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

interface MongoArtistRepository : MongoRepository<Artist, String>

@Repository
class MongoDbArtistRepository(
    private val mongoRepo: MongoArtistRepository
) : ArtistRepository {

    override fun isValidId(id: String): Boolean =
        ObjectId.isValid(id)

    override fun getAll(): List<Artist> =
        mongoRepo.findAll()

    override fun getAllByIds(ids: List<String>): List<Artist> =
        mongoRepo.findAllById(ids)

    override fun getByIdOrNull(id: String): Artist? =
        mongoRepo.findByIdOrNull(id)

    override fun existsById(id: String): Boolean =
        mongoRepo.existsById(id)

    override fun deleteById(id: String) =
        mongoRepo.deleteById(id)

    override fun add(artist: Artist) =
        mongoRepo.save(artist)
}
