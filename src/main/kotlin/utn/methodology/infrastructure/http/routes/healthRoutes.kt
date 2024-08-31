package kotlin.utn.methodology.infrastructure.http.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.utn.methodology.infrastructure.persistence.repositories.UserRepository
import kotlin.utn.methodology.infrastructure.http.actions.CreateUserAction
import kotlin.utn.methodology.application.commandhandlers.CreateUserHandler
import kotlin.utn.methodology.application.commands.CreateUserCommand
//import example.com.app.infrastructure.persistance.config.connectToMongoDB
//ver si este hace falta

fun Application.healthRoutes() {
    val mongoDatabase = connectToMongoDB()
    val userRepository = UserRepository(mongoDatabase)
    val confirmAction =
        CreateUserAction(CreateUserHandler(userRepository))
//    val findUserByIdAction = FindUserByIdAction(FindUserByIdHandler(userMongoUserRepository))
    routing {
        get("/health") {
            try
            {
                call.respond(HttpStatusCode.OK,  mapOf("message" to "ok"))
            }
            catch(e:Exception)
            {
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
            }
        }
    }
}
