package com.rorycd.artgallery.persistence

import com.rorycd.artgallery.models.Artwork
import com.rorycd.artgallery.models.ArtworkFilter
import org.bson.types.ObjectId
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

interface MongoArtworkRepository : MongoRepository<Artwork, String>

@Repository
class MongoDbArtworkRepository(
    private val mongoRepo: MongoArtworkRepository,
    private val mongoTemplate: MongoTemplate
) : ArtworkRepository {

    override fun isValidId(id: String): Boolean =
        ObjectId.isValid(id)

    override fun getAll(): List<Artwork> =
        mongoRepo.findAll()

    override fun find(filter: ArtworkFilter): List<Artwork> {
        val query = Query()

        // Apply filters
        filter.title?.let { query.addCriteria(Criteria.where("title").regex(it, "i")) }
        filter.artistId?.let { query.addCriteria(Criteria.where("artistId").`is`(it)) }
        filter.exhibitionId?.let { query.addCriteria(Criteria.where("exhibitionId").`is`(it)) }
        filter.framed?.let { query.addCriteria(Criteria.where("isFramed").`is`(it)) }
        filter.status?.let { query.addCriteria(Criteria.where("status").`is`(it)) }
        filter.year?.let { query.addCriteria(Criteria.where("year").`is`(it)) }
        filter.region?.let { query.addCriteria(Criteria.where("region").`is`(it)) }
        filter.orientation?.let { query.addCriteria(Criteria.where("orientation").`is`(it)) }
        filter.medium?.let { query.addCriteria(Criteria.where("medium").`is`(it)) }
        filter.maxPrice?.let { query.addCriteria(Criteria.where("price").lte(it)) }

        // Sorting direction
        val direction =
            if (filter.sortOrder?.lowercase() == "asc") Sort.Direction.ASC
            else Sort.Direction.DESC

        // Pagination defaults
        val pageNumber = filter.pageNumber
        val pageSize = (filter.pageSize.coerceIn(1, 100)) // Clamp range
        val sortField = filter.sortBy ?: "title"

        // Attach pageable to query
        val pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortField))
        query.with(pageable)

        return mongoTemplate.find(query, Artwork::class.java)
    }

    override fun getByIdOrNull(id: String): Artwork? =
        mongoRepo.findByIdOrNull(id)

    override fun existsById(id: String): Boolean =
        mongoRepo.existsById(id)

    override fun deleteById(id: String) =
        mongoRepo.deleteById(id)

    override fun add(artwork: Artwork) =
        mongoRepo.save(artwork)

    override fun count(): Long =
        mongoRepo.count()
}
