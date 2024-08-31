package kotlin.utn.methodology.infrastructure.http.actions

import kotlin.utn.methodology.application.commandhandlers.CreateUserHandler
import kotlin.utn.methodology.application.commands.CreateUserCommand

class CreateUserAction(private val handler: CreateUserHandler) {
    fun execute(body: CreateUserCommand) {
        body.validate().let {
            handler.handle(body)
        }
    }
}

