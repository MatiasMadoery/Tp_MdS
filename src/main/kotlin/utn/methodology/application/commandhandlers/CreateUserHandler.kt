package utn.methodology.application.commandhandlers

import com.mongodb.client.MongoDatabase
import utn.methodology.application.commands.CreateUserCommand
import java.util.UUID
import utn.methodology.domain.entities.User
import utn.methodology.infrastructure.persistence.repositories.UserRepository // aun no esta bien creado va Mongo

class CreateUserHandler (
            private val userRepository: UserRepository,
    )
{
    fun handle(command: CreateUserCommand)
    {
        val user = User.handle(
            UUID.randomUUID().toString(),
            command.Name,
            command.Username,
            command.Email,
            command.Password,
        )

        userRepository.Save(user)
    }

}

