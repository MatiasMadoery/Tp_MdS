package utn.methodology.infrastructure.http.actions

import utn.methodology.application.queries.PostsOfThePeopleIFollowQuery
import utn.methodology.application.queryhandlers.PostsOfThePeopleIFollowQueryHandler

class PostsOfThePeopleIFollowAction (private val queryHandler: PostsOfThePeopleIFollowQueryHandler){
    fun ejecutar(UserId: String): List<Post> {
        val query = PostsOfThePeopleIFollowQuery(UserId)
        return queryHandler.handle(query)
    }
}
//esto es dentro del repositorio son los datos que necesito solicitar
interface PostRepository {
    fun obtenerIdsSeguidos(usuarioId: String): List<String>
    fun obtenerPosteosPorUsuarios(ids: List<String>): List<Posteo>
}