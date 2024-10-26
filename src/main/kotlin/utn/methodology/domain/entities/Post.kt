package utn.methodology.domain.entities
import java.time.LocalDateTime
import java.time.ZoneOffset

data class Post (
    private val postId: String,
    private val userId: String,
    private val message: String,
    private val date: LocalDateTime
)

{
    companion object{



        fun fromPrimitives(primitives: Map<String, Any>):Post{
            val post = Post(
                primitives["postId"] as String,
                primitives["userId"] as String,
                primitives["message"] as String,
                LocalDateTime.ofEpochSecond(primitives["date"] as Long, 0, ZoneOffset.UTC)
            );
            return post
        }
    }

    fun toPrimitives(): Map<String, Any>{
        return mapOf(
            "postId" to postId,
            "userId" to userId,
            "message" to message,
            "date" to this.date.toEpochSecond(ZoneOffset.UTC)
        )
    }
    fun getUserId(): String = userId
}