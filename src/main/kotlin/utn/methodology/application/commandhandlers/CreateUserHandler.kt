package utn.methodology.application.commandhandlers

import utn.methodology.application.commands.CreateUserCommand
import utn.methodology.domain.entities.User
import utn.methodology.infrastructure.persistence.repositories.UserRepository // aun no esta bien echo va Mongo
import java.util.UUID

class CreateUserHandler (
            private val userRepository: UserRepository,
    )
{
    fun handle(command: CreateUserCommand)
    {
        val user = User(
            UUID.randomUUID().toString(),
            command.name,
            command.username,
            command.email,
            command.password,
        )

        userRepository.Save(user)
    }
}

