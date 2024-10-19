package utn.methodology.domain.entities

import kotlinx.serialization.Contextual
import java.util.Date

data class Post (
    private var postId: String,
    private var userId: String,
    private var message: String,
    @Contextual private var date: Date
)

{
    companion object{
        fun fromPrimitives(primitives: Map<String, String>):Post{
            val post = Post(
                primitives["postId"] as String,
                primitives["userId"] as String,
                primitives["message"] as String,
                primitives["date"] as Date
            )
            return post
        }
    }

    fun toPrimitives(): Map<String, Any>{
        return mapOf(
            "postId" to postId,
            "userId" to userId,
            "message" to message,
            "date" to date
        )
    }
    fun getUserId(): String = userId
}