package utn.methodology.infrastructure.persistence.repositories

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document
import utn.methodology.domain.entities.Post

interface PostRepositoryInterface {
    suspend fun getPostsByUserId(userId: String, order: String, limit: Int, offset: Int): List<Post>
    suspend fun findPostById(postId: String): Post?
    suspend fun deletePostById(postId: String): Boolean
}

class PostRepository(private val database: MongoDatabase) : PostRepositoryInterface {
    private var collection: MongoCollection<Document> = database.getCollection("posts") as MongoCollection<Document>

    fun savePost(post: Post): Boolean {
        val postDocument = Document(post.toPrimitives())
        return try {
            collection.insertOne(postDocument)
            true
        } catch (e: Exception) {
            println("Error al guardar el post: ${e.message}")
            false
        }
    }

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

    override suspend fun findPostById(postId: String): Post? {
        val query = Document("postId", postId)
        val document = collection.find(query).firstOrNull() ?: return null
        return documentToPost(document)
    }

    fun findPostsByUserIds(userIds: List<String>): List<Post> {

        val filter = Document("userId", Document("\$in", userIds))

        val documents = collection.find(filter)

        return documents.map { documentToPost(it) }.toList()
    }


    override suspend fun deletePostById(postId: String): Boolean {
        val query = Document("postId", postId)
        val deleteResult = collection.deleteOne(query)
        return deleteResult.deletedCount > 0
    }

    private fun documentToPost(document: Document): Post {
        return Post(
            postId = document.getString("postId"),
            userId = document.getString("userId"),
            message = document.getString("message"),
            date = document.getDate("date")
        )
    }
}