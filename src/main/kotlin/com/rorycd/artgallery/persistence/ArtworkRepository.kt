package com.rorycd.artgallery.persistence

import com.rorycd.artgallery.models.Artwork
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ArtworkRepository : MongoRepository<Artwork, String> {
    // Custom queries here
}

fun ArtworkRepository.isValidId(id: String): Boolean = ObjectId.isValid(id)
