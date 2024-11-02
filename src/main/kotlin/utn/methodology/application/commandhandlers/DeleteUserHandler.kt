package utn.methodology.application.commandhandlers

import io.ktor.server.plugins.*
import utn.methodology.application.commands.DeleteUserCommand
import utn.methodology.infrastructure.persistence.repositories.UserRepository

class DeleteUserHandler(private val userRepository: UserRepository) {
    fun handle(command: DeleteUserCommand){
        val user = userRepository.findById(command.id)
        if (user == null){
            throw NotFoundException("Not found user with id: ${command.id}")
        }
        userRepository.delete(user);
    }
}