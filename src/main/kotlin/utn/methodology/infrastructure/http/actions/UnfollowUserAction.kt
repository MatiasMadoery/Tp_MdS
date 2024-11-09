package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commandhandlers.UnfollowUserCommandHandler
import utn.methodology.application.commands.UnfollowUserCommand

data class UnfollowUserAction(private val unfollowUserCommandHandler: UnfollowUserCommandHandler){
    fun execute(userId: String, targetUserId: String){
        val command = UnfollowUserCommand(userId,targetUserId)
        unfollowUserCommandHandler.execute(command)
    }
}
