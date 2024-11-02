package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commandhandlers.CreatePostCommandHandler
import utn.methodology.application.commands.CreatePostCommand

data class CreatePostAction (private val savePostCommandHandler: CreatePostCommandHandler) {
    fun execute(idUser: String, message: String) {
        val command = CreatePostCommand(idUser = idUser, message = message)
        savePostCommandHandler.handle(command)
    }
}