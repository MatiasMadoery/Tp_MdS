package utn.methodology

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory
import utn.methodology.infrastructure.http.routes.createPostRoutes
import utn.methodology.infrastructure.http.routes.deletePostRoutes
import utn.methodology.infrastructure.http.routes.searchPostRoutes
import utn.methodology.infrastructure.persistence.configureDatabases
import utn.methodology.infrastructure.http.routes.userRoutes


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.errorHandler() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            utn.methodology.logError(call, cause)

            if (cause is NotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to cause.message))
            } else {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Internal server error"))
            }

        }
    }
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    configureDatabases()
    userRoutes()
    createPostRoutes()
    deletePostRoutes()
    searchPostRoutes()
    errorHandler()
}



fun logError(call: ApplicationCall, cause: Throwable) {
    val log = LoggerFactory.getLogger("ErrorLogger")
    val requestUri = call.request.uri
    log.error("Error at $requestUri: ${cause.localizedMessage}", cause)
}