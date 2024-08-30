package utn.methodology.infrastructure.persistence.repositories
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.bson.Document
import org.bson.types.ObjectId
import com.mongodb.client.model.Updates

class UserRepository(private val database:MongoDatabase) : UserRepository {
    private val collection = database.getCollection<User>("Users")

    fun Save (User: user): User {
        collection.insertOne(user)
        return user
    }

    fun SearchById(id: Long): User? {
        val idUser = ObjectId(id.toString())
        return collection.find(Document("id",idUser)).firstOrNull()
    }

    fun Update(User: user): User{
        val filter = Filters.eq("id",id)

        val update = Updates.combine(
            Updates.set("name",user.name),
            Updates.set("username",user.username),
            Updates.set("email",user.email),
            Updates.set("password",user.password)
        )

        collection.updateOne(filter,update)

        return user
    }

    fun ExistsByUsername(username: String): Boolean{
        val filter = Filters.eq("username",username)

        val result = collection.find(filter).firstOrNull()

        return result != null
    }


    fun ExistsByMail(email: String): Boolean{
        val filter = Filters.eq("email",email)

        val result = collection.find(filter).firstOrNull()

        return result != null
    }
}