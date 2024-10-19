package utn.methodology.infrastructure.http.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.application.commandhandlers.CreatePostHandler
import utn.methodology.application.commands.CreatePostCommand
import utn.methodology.infrastructure.http.actions.CreatePostAction
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.repositories.PostRepository

fun Application.createPostRoutes() {
    val mongoDatabase = connectToMongoDB()
    val postRepository = PostRepository(mongoDatabase)
    val postAction = CreatePostAction(CreatePostHandler(postRepository))

    routing {
        post("/posts") {
            val body = call.receive<CreatePostCommand>()
            try {
                if (body.message.length > 100) {
                    postAction.execute(body)
                    call.respond(HttpStatusCode.Created, mapOf("message" to "Entry too long!"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
            }
        }
    }
}
