package utn.methodology.infrastructure.persistence.repositories

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document
import utn.methodology.domain.entities.Post
import java.time.LocalDateTime

class PostRepository(private val database: MongoDatabase) {

    private var collection: MongoCollection<Document> = database.getCollection("posts")

    fun save(post: Post) {
        val newPost = Document(post.toPrimitives())

        println(newPost)
        collection.insertOne(newPost)

        println("post persisted")
    }

    fun delete(postId: String) {
        val filter = Document("id", postId)
        collection.deleteOne(filter)
    }

    fun findAllByUserId(idUser: String, order: String? = null, limit: Int? = null, offset: Int? = null): List<Post> {
        val filter = Document("idUser", idUser)
        val sort = if (order == "ASC") {
            Document("date", 1)
        } else if (order == "DESC") {
            Document("date", -1)
        } else {
            Document()
        }

        println(filter)
        val documents = collection.find(filter).toList()

        println(documents)

        if (limit != null) {
            //documents.limit(limit)
        }
        if (offset != null) {
            //documents.skip(offset)
        }

        println(documents)

        return documents.toList().map { document ->
            Post.fromPrimitives(document as Map<String, Any>)
        }
    }

    fun findPostsByFollowedUserIds(followedUserIds: List<String>, order: String? = null, limit: Int? = null, offset: Int? = null): List<Post> {
        val filter = Document("idUser", Document("\$in", followedUserIds))
        val sort = when (order) {
            "ASC" -> Document("date", 1)
            "DESC" -> Document("date", -1)
            else -> Document()
        }

        var query = collection.find(filter).sort(sort)
        if (limit != null) query = query.limit(limit)
        if (offset != null) query = query.skip(offset)

        return query.map { document ->
            Post.fromPrimitives(document as Map<String, Any>)
        }.toList()
    }


}