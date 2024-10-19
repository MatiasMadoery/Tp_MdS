package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commands.DeletePostCommand
import utn.methodology.application.commandhandlers.DeletePostCommandHandler

class DeletePostAction(private val handler: DeletePostCommandHandler) {
    suspend fun execute(postId: String, userId: String): Result<String> {
        val command = DeletePostCommand(postId, userId).validate()
        return handler.handle(command)
    }
}
