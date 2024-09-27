package utn.methodology.infrastructure.http.routes

import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.repositories.PostRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.application.commands.User

fun Application.deletePostRoutes() {
    val mongoDatabase = connectToMongoDB()
    val postRepository = PostRepository(mongoDatabase)

    routing {
        delete("/posts/{id}") {
            // Validar autenticación
            val userId = call.request.headers["User-Id"]?.toIntOrNull()

            if (userId == null) {
                call.respond(HttpStatusCode.Unauthorized, "Missing or invalid user ID")
                return@delete
            }

            val postId = call.parameters["id"]
            if (postId.isNullOrBlank()) {
                return@delete call.respond(HttpStatusCode.BadRequest, "Invalid post ID")
            }

            val post = postRepository.findPostById(postId)
            if (post == null) {
                return@delete call.respond(HttpStatusCode.NotFound, "Post not found")
            }

            // Validar que el usuario que solicita la eliminación es el dueño del post
            if (post.userId != user.id) {
                return@delete call.respond(HttpStatusCode.Forbidden, "You are not allowed to delete this post")
            }

            // Intentar eliminar el post
            val deleted = postRepository.deletePostById(postId)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Post deleted successfully")
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Failed to delete post")
            }
        }
    }
}
