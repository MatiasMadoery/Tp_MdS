package utn.methodology.infrastructure.persistence.repositories
import com.mongodb.client.MongoDatabase
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.UpdateOptions
import org.bson.Document
import utn.methodology.domain.entities.User

class UserRepository(private val database: MongoDatabase) {
    private var collection: MongoCollection<Document> = database.getCollection("users") as MongoCollection<Document>

    fun save(user: User) {
        val newUser = Document(user.toPrimitives())
        collection.insertOne(newUser)
    }

    fun findById(id: String): User? {
        val filter = Document("id", id)
        val primitives = collection.find(filter).firstOrNull() ?: return null
        return User.fromPrimitives(primitives as Map<String, String>)
    }

    fun update(user: User) {
        val options = UpdateOptions().upsert(true);
        val filter = Document("id", user.getId())
        val update = Document("\$set", user.toPrimitives())
        collection.updateOne(filter, update, options)
    }

    fun existsByUsername(username: String): User? {
        val filter = Document("username", username)
        val document = collection.find(filter).firstOrNull() ?: return null
        return User.fromPrimitives(document as Map<String, String>)
    }

    fun existByEmail(email: String): User? {
        val filter = Document("email", email)
        val document = collection.find(filter).firstOrNull() ?: return null
        return User.fromPrimitives(document as Map<String, String>)
    }

    fun delete(user: User){
        val filter = Document("id", user.getId());
        collection.deleteOne(filter)
    }

    fun followUser(followerId: String, userIdToFollow: String) {
        val filter = Document("id", followerId)
        val update = Document("\$addToSet", Document("following", userIdToFollow))
        collection.updateOne(filter, update)

        val followerFilter = Document("id", userIdToFollow)
        val followerUpdate = Document("\$addToSet", Document("followers", followerId))
        collection.updateOne(followerFilter, followerUpdate)
    }

    fun unfollowUser(followerId: String, userIdToUnfollow: String) {
        val filter = Document("id", followerId)
        val update = Document("\$pull", Document("following", userIdToUnfollow))
        collection.updateOne(filter, update)

        val followerFilter = Document("id", userIdToUnfollow)
        val followerUpdate = Document("\$pull", Document("followers", followerId))
        collection.updateOne(followerFilter, followerUpdate)
    }
}