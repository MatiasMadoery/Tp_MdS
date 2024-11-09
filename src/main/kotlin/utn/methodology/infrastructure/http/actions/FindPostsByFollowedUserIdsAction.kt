package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commandhandlers.FindPostsByFollowedUserIdsCommandHandler
import utn.methodology.application.commands.FindPostsByFollowedUserIdsCommand
import utn.methodology.infrastructure.http.dto.FindAllPostResult

data class FindPostsByFollowedUserIdsAction(private val handler: FindPostsByFollowedUserIdsCommandHandler){
    fun execute(userId: String, order: String? = null, limit: Int? = null, offset: Int? = null): FindAllPostResult {
        val command = FindPostsByFollowedUserIdsCommand(userId, order, limit, offset)
        val result = handler.execute(command)
        return FindAllPostResult(result.map { it.toPrimitives() })
    }
}
