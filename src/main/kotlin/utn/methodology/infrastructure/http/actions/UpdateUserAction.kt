package utn.methodology.infrastructure.http.actions


import io.ktor.http.*
import utn.methodology.application.commandhandlers.UpdateUserHandler
import utn.methodology.application.commands.UpdateUserCommand

class UpdateUserAction(
    private val handler: UpdateUserHandler
) {
    fun execute(body: UpdateUserCommand){
        body.validate().let {
            handler.handle(it)
        }
    }
}
