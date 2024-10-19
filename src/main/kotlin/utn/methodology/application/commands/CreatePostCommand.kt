package utn.methodology.application.commands

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable()
data class CreatePostCommand(
    var postId: String,
    var userId: String,
    var message: String,
    @Contextual var date: Date
)
{
    fun validate(): CreatePostCommand{
        checkNotNull(postId){throw IllegalArgumentException("PostId must be defined")}
        checkNotNull(userId){throw IllegalArgumentException("UserId Name must be defined")}
        checkNotNull(message){throw  IllegalArgumentException("Message must be defined")}
        checkNotNull(date){throw  IllegalArgumentException("Date must be defined")}

        return this
    }
}