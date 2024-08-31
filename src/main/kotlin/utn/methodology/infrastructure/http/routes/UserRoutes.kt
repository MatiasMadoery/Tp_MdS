package utn.methodology.infrastructure.http.routes

import utn.methodology.application.commands.CreateUserCommand
import utn.methodology.application.commandhandlers.CreateUserHandler
import utn.methodology.infrastructure.http.actions.CreateUserAction
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.repositories.UserRepository
import com.mongodb.client.*
import io.ktor.server.config.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.userRoutes() {
    val mongoDatabase = connectToMongoDB()
    val userRepository = UserRepository(mongoDatabase)
    val createUserAction = CreateUserAction(CreateUserHandler(userRepository))
//    val findUserByIdAction = FindUserByIdAction(FindUserByIdHandler(userMongoUserRepository))

    routing {
        post("/users") {
            val body = call.receive<CreateUserCommand>()
            try
            {
                createUserAction.execute(body)
                call.respond(HttpStatusCode.Created, mapOf("message" to "User registered"))
            }
            catch(e:Exception)
            {
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
            }
        }
    }
}
