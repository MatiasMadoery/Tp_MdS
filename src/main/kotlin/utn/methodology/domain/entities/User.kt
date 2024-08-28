package utn.methodology.domain.entities
import ch.qos.logback.core.status.Status

data class User (
    private val id : String,
    private var name : String,
    private var username : String,
    private var email : String,
    private var password : String
)

{
    companion object {
        fun fromPrimitives(primitives: Map<String, String>): User{
            val user = User(
                primitives["id"] as String,
                primitives["name"] as String,
                primitives["username"] as String,
                primitives["email"] as String,
                primitives["password"] as String
            );
            return user;
        }
    }

    fun getId(): String{
        return this.id;
    }

    fun update(name: String, username: String, email: String, password: String){
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    fun toPrimitives(): Map<String, String>{
        return mapOf(
            "id" to this.id,
            "name" to this.name,
            "username" to this.username,
            "email" to  this.email,
            "password" to this.password
        )
    }
}



