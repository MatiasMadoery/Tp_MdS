package utn.methodology.domain.entities

import java.time.format.DateTimeFormatter
import java.util.*


data class Post(
    val id: String = UUID.randomUUID().toString(),
    val idUser: String,
    var message: String,
    val date: String
) {
    fun toPrimitives(): Map<String, String> {
        return mapOf(
            "id" to id,
            "idUser" to idUser,
            "message" to message,
            "date" to date.toString()
        )
    }

    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        fun fromPrimitives(primitives: Map<String, Any>): Post {
            return Post(
                id = primitives["id"] as String,
                idUser = primitives["idUser"] as String,
                message = primitives["message"] as String,
                date = primitives["date"] as String
            )
        }
    }
}