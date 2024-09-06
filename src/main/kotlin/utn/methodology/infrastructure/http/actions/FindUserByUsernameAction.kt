package utn.methodology.infrastructure.http.actions

import utn.methodology.application.queries.FindUserByUsernameQuery
import utn.methodology.application.queryhandlers.FindUserByUsernameHandler

class FindUserByUsernameAction (private val handler: FindUserByUsernameHandler)
{
    fun execute(query: FindUserByUsernameQuery){
        query.validate().let{
             handler.handle(it)
        }
    }

}