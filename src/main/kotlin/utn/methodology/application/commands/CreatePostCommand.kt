package utn.methodology.application.commands

class CreatePostCommand(
    val idUser: String,
    val message: String
) {
    fun validate(): CreatePostCommand {
        require(idUser.isNotBlank()) { "User ID must be defined" }
        require(message.isNotBlank()) { "Message must be defined" }
        return this
    }
}