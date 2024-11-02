package utn.methodology.application.commandhandlers

import io.ktor.server.plugins.*
import utn.methodology.application.commands.UpdateUserCommand
import utn.methodology.infrastructure.persistence.repositories.UserRepository


class UpdateUserHandler(
    private val userRepository: UserRepository
) {
    fun handle(command: UpdateUserCommand){
        val user = userRepository.findById(command.id)
        if (user == null){
            throw  NotFoundException("Not found user with id: ${command.id}")
        }
        user.update(command.name, command.username, command.email, command.password)
        userRepository.update(user)
    }
}