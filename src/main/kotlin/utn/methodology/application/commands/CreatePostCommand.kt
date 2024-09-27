package utn.methodology.application.commands

import java.util.*

data class CreatePostCommand(
    var postId: String,
    var userId: String,
    var message: String,
    var date: Date
)
