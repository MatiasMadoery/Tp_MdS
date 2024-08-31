package kotlin.utn.methodology.infrastructure.http.routes

//import example.com.app.infrastructure.persistance.config.connectToMongoDB
//import example.com.eventBus
//import example.com.app.infrastructure.utils.Response
//ver si hacen falta
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
//va kotlin?
import kotlin.utn.methodology.infrastructure.persistence.repositories.UserRepository
import kotlin.utn.methodology.infrastructure.http.actions.CreateUserAction
import kotlin.utn.methodology.application.commandhandlers.CreateUserHandler
import kotlin.utn.methodology.application.commands.CreateUserCommand

fun Application.UserRoutes() {
    val mongoDatabase = connectToMongoDB() //ver si hay que importar
    val userRepository = UserRepository(mongoDatabase)
    val createuseraction =
        CreateUserAction(CreateUserHandler(userRepository, eventBus))
//    val findUserByIdAction = FindUserByIdAction(FindUserByIdHandler(userMongoUserRepository))

    routing {
        post("/user") {
            val body = call.receive<CreateUserCommand>()
            try
            {
                createuseraction.execute(body)
                call.respond(HttpStatusCode.Created, mapOf("message" to "User registered"))
            }
            catch(e:Exception)
            {
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
            }
        }
    }
}
