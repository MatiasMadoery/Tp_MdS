package utn.methodology.infrastructure.http.routes

import utn.methodology.application.commands.CreateUserCommand
import utn.methodology.application.commandhandlers.CreateUserHandler
import utn.methodology.infrastructure.http.actions.CreateUserAction
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.repositories.UserRepository
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
    val userRepository = UserRepository(mongoDatabase)
    val createUserAction = CreateUserAction(CreateUserHandler(userRepository))
    val findUserByUsernameAction = FindUserByUsernameAction(FindUserByUsernameHandler(userRepository))

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

        get("/users/{username}") {

            val query = FindUserByUsernameQuery(call.parameters["username"].toString())

            val result = findUserByUsernameAction.execute(query)

            call.respond(HttpStatusCode.OK, result)

            }

    }
}
