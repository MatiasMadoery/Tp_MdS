package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commandhandlers.FollowUserCommandHandler
import utn.methodology.application.commands.FollowUserCommand

data class FollowUserAction(private val followUserCommandHandler: FollowUserCommandHandler) {
    fun execute(userId: String, targetUserId: String){
        val command = FollowUserCommand(userId, targetUserId)
        followUserCommandHandler.execute(command)
    }
}
