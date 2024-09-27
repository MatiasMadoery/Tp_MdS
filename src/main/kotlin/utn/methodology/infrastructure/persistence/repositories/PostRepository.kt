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

    fun deletePostById(postId: String) : Boolean{
        val filter = Document("postId", postId)
        val result = collection.deleteOne(filter)

        return result.deletedCount > 0
    }

    private fun documentToPost(document: Document): Post {
        return Post(
            postId = document.getString("postId"),
            userId = document.getString("userId"),
            message = document.getString("message"),
            date = document.getDate("date")
        )
    }

    fun findPostById(postId: String) : Post? {
        val filter = Document("postId", postId)
        val result = collection.find(filter).firstOrNull()

        if(result == null){
            return null
        }

        val primitives = result.toMap().mapKeys { it.key as String } as Map<String, String>

        return Post.fromPrimitives(primitives)
    }
}