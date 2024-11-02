package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commandhandlers.FindAllPostsByUserIdHandler
import utn.methodology.application.commands.FindAllPostsByUserIdCommand
import utn.methodology.domain.entities.Post
import utn.methodology.infrastructure.http.dto.FindAllPostResult

class FindAllPostsByUserIdAction(private val handler: FindAllPostsByUserIdHandler) {
    fun execute(userId: String, order: String? = null, limit: Int? = null, offset: Int? = null): FindAllPostResult {
        val command = FindAllPostsByUserIdCommand(userId, order, limit, offset)
        val result = handler.handle(command)

        return FindAllPostResult(result.map { it.toPrimitives() })
    }
}