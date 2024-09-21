package utn.methodology.infrastructure.http.routes

import utn.methodology.application.commands.CreateUserCommand
import utn.methodology.application.commandhandlers.CreateUserHandler
import utn.methodology.infrastructure.http.actions.CreateUserAction
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.repositories.PostRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.application.queries.FindUserByUsernameQuery
import utn.methodology.application.queryhandlers.FindUserByUsernameHandler
import utn.methodology.infrastructure.http.actions.FindUserByUsernameAction

fun Application.userRoutes() {
    val mongoDatabase = connectToMongoDB()
    val postRepository = PostRepository(mongoDatabase)
    val createUserAction = CreateUserAction(CreateUserHandler(userRepository))
    val findUserByUsernameAction = FindUserByUsernameAction(FindUserByUsernameHandler(userRepository))

routing {
    delete("/posts/{id}") {
        val user = call.principal<UserIdPrincipal>() // Obtener el usuario autenticado

        if (user != null) {
            val postId = call.parameters["id"]?.toIntOrNull()

            if (postId != null) {
                val post = postRepository.getPostById(postId) // Buscar el post en el repositorio

                if (post != null && post.userId == user.name) {
                    // El post le pertenece al usuario
                    val wasDeleted = postRepository.deletePostById(postId)

                    if (wasDeleted) {
                        call.respondText("Post deleted successfully", status = HttpStatusCode.OK)
                    } else {
                        call.respondText("Error deleting post", status = HttpStatusCode.InternalServerError)
                    }
                } else {
                    call.respondText("Post not found or not authorized", status = HttpStatusCode.Forbidden)
                }
            } else {
                call.respondText("Invalid Post ID", status = HttpStatusCode.BadRequest)
            }
        } else {
            call.respondText("User not authenticated", status = HttpStatusCode.Unauthorized)
        }
    }

    }

}
//fun deletePostById(id: Int): Boolean {
//    val post = getPostById(id)
//    return if (post != null) {
//        posts.remove(post)
//        true
//    } else {
//        false
//    }
//}