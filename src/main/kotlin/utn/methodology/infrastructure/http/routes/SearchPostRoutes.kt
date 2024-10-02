package utn.methodology.infrastructure.http.routes


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.repositories.PostRepository

fun Application.searchPostRoutes() {
    val mongoDatabase = connectToMongoDB()
    val postRepository = PostRepository(mongoDatabase)


    routing {
        get("/posts"){
            val userId = call.parameters["userId"] ?: return@get call.respondText(
                "Missing userId", status = HttpStatusCode.BadRequest
            )

            val order = call.request.queryParameters["order"] ?: "ASC"
            val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 10
            val offset = call.request.queryParameters["offset"]?.toIntOrNull() ?: 0

            val posts = postRepository.getPostsByUserId(userId, order, limit, offset)

            call.respond(posts)
        }
    }
}