package utn.methodology.application.commandhandlers

import utn.methodology.application.commands.CreateUserCommand
import java.util.UUID
import utn.methodology.domain.entities.User
import utn.methodology.infrastructure.persistence.repositories.UserRepository

class ConfirmHandler (
            private val userRepository: UserRepository,
    )
{
    fun handle(command: CreateUserCommand)
    {
        val user = User.handle(
            UUID.randomUUID().toString(),
            command.Id,
            command.Name,
            command.Username,
            command.Email,
            command.Password,
        )

        userRepository.Update(User)
    }

}

