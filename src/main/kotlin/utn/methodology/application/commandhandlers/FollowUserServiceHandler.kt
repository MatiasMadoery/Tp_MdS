package utn.methodology.application.commandhandlers

import io.ktor.server.plugins.*
import utn.methodology.infrastructure.persistence.repositories.UserRepository
import utn.methodology.application.commands.UserFollowCommand

class FollowUserServiceHandler (
    val userRepository: UserRepository
){
    fun handle(command: UserFollowCommand) {
        val currentUser = userRepository.findById(command.followerId)
        val following = userRepository.findById(command.followedId)

        if (currentUser == null || following == null) {
            throw NotFoundException("One or both users do not exist")
        }

        if (following.getId() == currentUser.getId()) {
            throw NotFoundException("You can't follow yourself")
        }

        currentUser.follow(following)
        following.addFollower(currentUser)
    }
    }

