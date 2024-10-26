package utn.methodology.infrastructure.http.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.application.commandhandlers.CreatePostHandler
import utn.methodology.application.commands.CreatePostCommand
import utn.methodology.infrastructure.http.actions.CreatePostAction
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.repositories.PostRepository
import utn.methodology.infrastructure.persistence.repositories.UserRepository

fun Application.createPostRoute() {
    val mongoDatabase = connectToMongoDB()
    val postRepository = PostRepository(mongoDatabase)
    val userRepository = UserRepository(mongoDatabase)
    val postAction = CreatePostAction(CreatePostHandler(postRepository, userRepository))

    routing {
        post("/posts") {
            try {
                // Recibe el cuerpo de la solicitud
                val body = call.receive<CreatePostCommand>()
                // Verifica la longitud del mensaje
                if (body.message.length > 2) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Message length exceeds 100 characters"))
                } else {
                    postAction.execute(body)
                    call.respond(HttpStatusCode.Created, mapOf("message" to "Entry ok!"))
                }
            } catch (e: Exception) {
                println("me rompi $e")
            }
        }
    }
}

