package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commandhandlers.CreatePostHandler
import utn.methodology.application.commands.CreatePostCommand


class  CreatePostAction (private val handler: CreatePostHandler) {
    fun execute(body: CreatePostCommand) {
        body.validate().let {
            handler.handle(it)
        }
    }
}