package utn.methodology.domain.entities

import java.util.Date

data class Post (
    private var userId: String,
    private var message: String,
    private var date: Date
)

{
    companion object{
        fun fromPrimitives(primitives: Map<String, String>):Post{
            val post = Post(
                primitives["userId"] as String,
                primitives["message"] as String,
                primitives["date"] as Date
            )
            return post
        }
    }

    fun toPrimitives(): Map<String, Any>{
        return mapOf(
            "userId" to userId,
            "message" to message,
            "date" to date
        )
    }
}