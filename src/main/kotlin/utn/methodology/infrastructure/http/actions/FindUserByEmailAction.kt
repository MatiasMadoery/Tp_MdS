package utn.methodology.infrastructure.http.actions

import utn.methodology.application.queries.FindUserByEmailQuery
import utn.methodology.application.queryhandlers.FindUserByEmailHandler


class FindUserByEmailAction(private  val handler: FindUserByEmailHandler){
    fun execute(query: FindUserByEmailQuery): Map<String,String> {
        query.validate().let {
            return handler.handle(it)
        }
    }
}