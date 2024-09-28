package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commands.UserFollowCommand
import utn.methodology.application.commandhandlers.FollowUserServiceHandler

class FollowUserAction(private val handler: FollowUserServiceHandler){
    fun execute(body: UserFollowCommand){
        body.validate().let {
            handler.handle(it)
        }
    }
}