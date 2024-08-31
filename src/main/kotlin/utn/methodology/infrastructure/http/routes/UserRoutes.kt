package kotlin.utn.methodology.infrastructure.http.routes

import example.com.app.application.commandHandlers.ConfirmShippingHandler
import example.com.app.application.commands.ConfirmShippingCommand
import example.com.app.infrastructure.http.actions.CreateUserAction
import example.com.app.infrastructure.persistance.config.connectToMongoDB
import example.com.app.infrastructure.persistance.repositories.ShippingMongoRepository
import example.com.eventBus
import example.com.app.infrastructure.utils.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.shippingRoutes() {
    val mongoDatabase = connectToMongoDB() // Conexión a la base de datos

    val shippingMongoRepository = ShippingMongoRepository(mongoDatabase) // Inyección del repositorio

    val createuseraction =
        CreateUserAction(ConfirmShippingHandler(shippingMongoRepository, eventBus)) // Inyección del manejador de la acción

//    val findUserByIdAction = FindUserByIdAction(FindUserByIdHandler(userMongoUserRepository))

    routing {
        // POST /shippings crea un nuevo envío
        post("/register") {
            // Recibe lo que ingreso el usuario
            val body = call.receive<ConfirmShippingCommand>()
            try
            {
                //Mensaje de éxito si esta bien
                createuseraction.execute(body)
                call.respond(HttpStatusCode.Created, mapOf("message" to "User registered"))
            }
            catch(e:Exception)
            {
                // Manejar errores
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
            }
        }
    }
}
