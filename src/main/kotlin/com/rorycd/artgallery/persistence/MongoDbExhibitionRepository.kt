package com.rorycd.artgallery.persistence

import com.rorycd.artgallery.models.Exhibition
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

interface MongoExhibitionRepository : MongoRepository<Exhibition, String>

@Repository
class MongoDbExhibitionRepository(
    private val mongoRepo: MongoExhibitionRepository
) : ExhibitionRepository {

    override fun isValidId(id: String): Boolean =
        ObjectId.isValid(id)

    override fun getAll(): List<Exhibition> =
        mongoRepo.findAll()

    override fun getByIdOrNull(id: String): Exhibition? =
        mongoRepo.findByIdOrNull(id)

    override fun existsById(id: String): Boolean =
        mongoRepo.existsById(id)

    override fun deleteById(id: String) =
        mongoRepo.deleteById(id)

    override fun add(exhibition: Exhibition) =
        mongoRepo.save(exhibition)
}
