package utn.methodology.application.commands

data class CreatePostCommand(
    var userId: String,
    var message: String,
)
{
    fun validate(): CreatePostCommand{
        checkNotNull(userId){throw IllegalArgumentException("UserId Name must be defined")}
        checkNotNull(message){throw  IllegalArgumentException("Message must be defined")}

        return this
    }
}