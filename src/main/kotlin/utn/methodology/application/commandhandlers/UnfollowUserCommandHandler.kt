package utn.methodology.application.commandhandlers

import utn.methodology.application.commands.UnfollowUserCommand
import utn.methodology.infrastructure.persistence.repositories.UserRepository

data class UnfollowUserCommandHandler(private val userRepository: UserRepository){
    fun execute(command: UnfollowUserCommand){
        userRepository.unfollowUser(command.userId,command.targetUserId)
    }
}
