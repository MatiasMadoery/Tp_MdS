package utn.methodology.infrastructure.persistence.repositories

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document
import utn.methodology.domain.entities.Post

interface PostRepositoryInterface {
    suspend fun getPostsByUserId(userId: String, order: String, limit: Int, offset: Int): List<Post>
}

class PostRepository (private val database: MongoDatabase) : PostRepositoryInterface {
    private var collection: MongoCollection<Document> = database.getCollection("posts") as MongoCollection<Document>

    override suspend fun getPostsByUserId(
        userId: String,
        order: String,
        limit: Int,
        offset: Int
    ): List<Post> {
        val sortOrder = if (order == "ASC") 1 else -1

        val query = Document("userId", userId)
        val sort = Document("date", sortOrder)

        return collection.find(query)
            .sort(sort)
            .skip(offset)
            .limit(limit)
            .map { documentToPost(it) }
            .toList()
    }

    private fun documentToPost(document: Document): Post {
        return Post(
            userId = document.getString("userId"),
            message = document.getString("message"),
            date = document.getDate("date")
        )
    }
    fun getIdsTracked (usuarioId: String): List<String>
    fun getPostsByUsers(ids : List<String>) : List<Post>
}