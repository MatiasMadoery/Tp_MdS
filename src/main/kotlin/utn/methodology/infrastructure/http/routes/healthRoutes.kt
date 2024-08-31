package kotlin.utn.methodology.infrastructure.http.routes

import example.com.app.application.commandHandlers.ConfirmShippingHandler
import example.com.app.application.commands.ConfirmShippingCommand
import example.com.app.infrastructure.http.actions.ConfirmShippingAction
import example.com.app.infrastructure.persistance.config.connectToMongoDB
import example.com.app.infrastructure.persistance.repositories.ShippingMongoRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.utn.methodology.infrastructure.http.actions.CreateUserAction

fun Application.healthRoutes() {
    val mongoDatabase = connectToMongoDB() // Conexión a la base de datos

    val shippingMongoRepository = ShippingMongoRepository(mongoDatabase) // Inyección del repositorio

    val confirmShippingAction =
        CreateUserAction(ConfirmShippingHandler(shippingMongoRepository)) // Inyección del manejador de la acción

//    val findUserByIdAction = FindUserByIdAction(FindUserByIdHandler(userMongoUserRepository))
    routing {
        // POST /shippings crea un nuevo envío
        get("/health") {
            try
            {
                //Mensaje de éxito si esta bien
                call.respond(HttpStatusCode.OK,  mapOf("message" to "ok"))
            }
            catch(e:Exception)
            {
                // Manejar errores
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
            }
        }
    }
}
