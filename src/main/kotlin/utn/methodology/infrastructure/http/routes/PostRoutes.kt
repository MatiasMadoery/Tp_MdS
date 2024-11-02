package utn.methodology.infrastructure.http.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.application.commandhandlers.CreatePostCommandHandler
import utn.methodology.application.commandhandlers.DeletePostCommandHandler
import utn.methodology.application.commandhandlers.FindAllPostsByUserIdHandler
import utn.methodology.application.commands.FindAllPostsByUserIdCommand
import utn.methodology.infrastructure.http.actions.CreatePostAction
import utn.methodology.infrastructure.http.actions.DeletePostAction
import utn.methodology.infrastructure.http.actions.FindAllPostsByUserIdAction
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.repositories.PostRepository

fun Application.postRoutes() {
    val mongoDatabase = connectToMongoDB()
    val postRepository = PostRepository(mongoDatabase)
    val savePostAction = CreatePostAction(CreatePostCommandHandler(postRepository))
    val deletePostAction = DeletePostAction(DeletePostCommandHandler(postRepository))
    val findAllPostsByUserIdAction = FindAllPostsByUserIdAction(FindAllPostsByUserIdHandler(postRepository))

    routing {
        post("/posts") {
            val body = call.receive<Map<String, String>>()
            val idUser = body["idUser"] ?: return@post call.respond(HttpStatusCode.BadRequest, mapOf("message" to "User ID is required"))
            val message = body["message"] ?: return@post call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Message is required"))

            try {
                savePostAction.execute(idUser, message)
                call.respond(HttpStatusCode.Created, mapOf("message" to "Post saved successfully"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
            }
        }

        delete("/posts/{postId}") {
            val postId = call.parameters["postId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Post ID is required"))

            try {
                deletePostAction.execute(postId)
                call.respond(HttpStatusCode.Accepted, mapOf("message" to "Post deleted successfully"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
            }
        }

        get("/posts/user/{idUser}") {
            val idUser = call.parameters["idUser"] ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("message" to "User ID is required"))
            val order = call.request.queryParameters["order"]
            val limit = call.request.queryParameters["limit"]?.toIntOrNull()
            val offset = call.request.queryParameters["offset"]?.toIntOrNull()

            try {
                val posts = findAllPostsByUserIdAction.execute(idUser, order, limit, offset)
                call.respond(HttpStatusCode.OK, posts)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
            }
        }

        //posts/user/user123?order=DESC&limit=10&offset=0 Linea para probar en Postman :)
    }
}