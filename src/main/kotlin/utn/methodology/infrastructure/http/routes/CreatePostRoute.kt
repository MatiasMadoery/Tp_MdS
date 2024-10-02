package utn.methodology.infrastructure.http.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.repositories.UserRepository


fun Application.createPostRoutes() {
    val mongoDatabase = connectToMongoDB()
    val postRepository = PostRepository(mongoDatabase)
    val postAction = PostAction(PostHandler(postRepository))

    routing {
        post("/posts") {
            val body = call.receive<PostCommand>()
            try {
                if (body.message.Length > 100) {
                    postAction.execute(body)
                    call.respond(HttpStatusCode.Created, mapOf("message" to "Entry too long!"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
            }
        }
    }
}
