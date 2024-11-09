package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commandhandlers.DeletePostCommandHandler
import utn.methodology.application.commands.DeletePostCommand

class DeletePostAction(private val deletePostCommandHandler: DeletePostCommandHandler) {
    fun execute(postId: String) {
        val command = DeletePostCommand(postId = postId)
        deletePostCommandHandler.handle(command)
    }
}
