package utn.methodology.application.commandhandlers

import io.ktor.server.plugins.*
import utn.methodology.application.commands.FollowUserCommand
import utn.methodology.infrastructure.persistence.repositories.UserRepository

data class FollowUserCommandHandler(private val userRepository: UserRepository){
    fun execute(command: FollowUserCommand){
        userRepository.followUser(command.userId,command.targetUserId)
    }
}
