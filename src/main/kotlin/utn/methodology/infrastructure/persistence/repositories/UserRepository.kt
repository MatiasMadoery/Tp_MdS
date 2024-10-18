package utn.methodology.infrastructure.persistence.repositories
import com.mongodb.client.MongoDatabase
import com.mongodb.client.MongoCollection
import org.bson.Document
import utn.methodology.domain.entities.User

class UserRepository(private val database: MongoDatabase) {
    private var collection: MongoCollection<Document> = database.getCollection("users") as MongoCollection<Document>

    fun save (user: User) {
        val newUser = Document(user.toPrimitives())
        collection.insertOne(newUser)
    }

    fun findById(id: String): User? {
        val filter = Document("id", id)

        val primitives = collection.find(filter).firstOrNull() ?: return null

        return User.fromPrimitives(primitives as Map<String, String>)
    }

    fun findFollowedIdsByUserId(userId: String): List<String> {

        val filter = Document("id", userId)

        val followedUsers = collection.find(filter)

        return followedUsers.map { it.getString("following") }.toList()
    }


    fun update(user: User): Boolean{
        val filter = Document("id", user.getId())
        val update = Document("\$set", user.toPrimitives())
        val result = collection.updateOne(filter, update)

        return result.matchedCount > 0
    }

    fun existsByUsername(username: String): User? {
        val filter = Document("username", username)

        val document = collection.find(filter).firstOrNull() ?: return null

        return User.fromPrimitives(document as Map<String, String>)
    }


    fun existsByMail(email: String): User?{
        val filter = Document("email",email)

        val document = collection.find(filter).firstOrNull() ?: return null

        val primitives = document.mapNotNull {
            if (it.value is String) it.key to it.value as String else return null
        }.toMap()

        return User.fromPrimitives(primitives)
    }
}