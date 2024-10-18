package utn.methodology.infrastructure.http.actions

import org.bson.Document
import utn.methodology.application.queries.PostsOfThePeopleIFollowQuery
import utn.methodology.application.queryhandlers.PostsOfThePeopleIFollowQueryHandler
import utn.methodology.domain.entities.Post
import utn.methodology.domain.entities.User
import utn.methodology.infrastructure.persistence.repositories.PostRepository
import utn.methodology.infrastructure.persistence.repositories.UserRepository

class PostsOfThePeopleIFollowAction (private val queryHandler: PostsOfThePeopleIFollowQueryHandler){
    fun ejecutar(followedId: String): List<Post> {
        val query = PostsOfThePeopleIFollowQuery(followedId)
        return queryHandler.handle(query)
    }
}

/*esto es dentro del repositorio son los datos que necesito solicitar
interface PostRepository {
    fun obtenerIdsSeguidos(usuarioId: String): List<String>
    fun obtenerPosteosPorUsuarios(ids: List<String>): List<Posteo>
}

 */
