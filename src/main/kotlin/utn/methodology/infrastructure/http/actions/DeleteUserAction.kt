package utn.methodology.infrastructure.http.actions

import io.ktor.http.*
import utn.methodology.application.commandhandlers.DeleteUserHandler
import utn.methodology.application.commands.DeleteUserCommand

class DeleteUserAction(
    private val handler: DeleteUserHandler
) {
    fun execute(parameters: Parameters){
        val command = DeleteUserCommand(parameters["id"].toString())
        command.validate().let{
            handler.handle(it)
        }
    }
}