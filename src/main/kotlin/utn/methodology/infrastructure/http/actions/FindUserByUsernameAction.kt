package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commandhandlers.FindAllPostsByUserIdHandler
import utn.methodology.application.commands.FindAllPostsByUserIdCommand
import utn.methodology.application.queries.FindUserByUsernameQuery
import utn.methodology.application.queryhandlers.FindUserByUsernameHandler
import utn.methodology.domain.entities.Post

class FindUserByUsernameAction (private val handler: FindUserByUsernameHandler)
{
    fun execute(query: FindUserByUsernameQuery): Map<String, String>{
        query.validate().let{
            return handler.handle(it)
        }
    }

}