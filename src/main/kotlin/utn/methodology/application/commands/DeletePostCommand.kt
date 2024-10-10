package utn.methodology.application.commands

data class DeletePostCommand(
    val postId: String,
    val userId: String
) {
    fun validate(): DeletePostCommand {
        if (postId.isBlank()) throw IllegalArgumentException("Invalid post ID")
        return this
    }
}
