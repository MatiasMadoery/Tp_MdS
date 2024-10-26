package utn.methodology.infrastructure.http.routes

import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.repositories.PostRepository
import utn.methodology.infrastructure.http.actions.DeletePostAction
import utn.methodology.application.commandhandlers.DeletePostCommandHandler
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.deletePostRoutes() {
    val mongoDatabase = connectToMongoDB()
    val postRepository = PostRepository(mongoDatabase)
    val deletePostCommandHandler = DeletePostCommandHandler(postRepository)
    val deletePostAction = DeletePostAction(deletePostCommandHandler)


    routing {
        delete("/posts/{id}") {
            val userId = call.parameters["id"]
            if (userId.isNullOrBlank()) {
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

            if (post.getUserId() != userId) {
                return@delete call.respond(HttpStatusCode.Forbidden, "You do not have permission to delete this post")
            }

            val result = deletePostAction.execute(postId, userId)
            result.fold(
                onSuccess = { message -> call.respond(HttpStatusCode.OK, message) },
                onFailure = { error -> call.respond(HttpStatusCode.BadRequest, error.message ?: "Error deleting post") }
            )
        }
    }
}
