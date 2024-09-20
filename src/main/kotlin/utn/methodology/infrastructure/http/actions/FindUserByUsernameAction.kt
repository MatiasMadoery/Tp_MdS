package utn.methodology.infrastructure.http.actions

import utn.methodology.application.queries.FindUserByUsernameQuery
import utn.methodology.application.queryhandlers.FindUserByUsernameHandler

class FindUserByUsernameAction (private val handler: FindUserByUsernameHandler)
{
    fun execute(query: FindUserByUsernameQuery): Map<String, String>{
        query.validate().let{
            return handler.handle(it)
        }
    }

}