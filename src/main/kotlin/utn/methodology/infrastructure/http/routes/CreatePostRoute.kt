package utn.methodology.infrastructure.http.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.infrastructure.persistence.connectToMongoDB


fun Application.createPostRoutes() {
    val mongoDatabase = connectToMongoDB()
    val createPostAction = CreatePostAction(CreatePostHandler(CreatePostRepository))

    routing {
        post("/posts") {
            val body = call.receive<CreatePostCommand>()
            try {
                if (body.message.Length > 100) {
                    createPostAction.execute(body)
                    call.respond(HttpStatusCode.Created, mapOf("message" to "Entry too long!"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
            }
        }
    }
}
