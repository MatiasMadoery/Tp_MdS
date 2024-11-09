package utn.methodology.infrastructure.http.routes

import utn.methodology.application.commands.CreateUserCommand
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.repositories.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.application.commandhandlers.*
import utn.methodology.application.commands.UpdateUserCommand
import utn.methodology.application.queries.FindUserByEmailQuery
import utn.methodology.application.queries.FindUserByUsernameQuery
import utn.methodology.application.queryhandlers.FindUserByEmailHandler
import utn.methodology.application.queryhandlers.FindUserByUsernameHandler
import utn.methodology.infrastructure.http.actions.*

fun Application.userRoutes() {
    val mongoDatabase = connectToMongoDB()
    val userRepository = UserRepository(mongoDatabase)
    val createUserAction = CreateUserAction(CreateUserHandler(userRepository))
    val findUserByUsernameAction = FindUserByUsernameAction(FindUserByUsernameHandler(userRepository))
    val findUserByEmailAction = FindUserByEmailAction(FindUserByEmailHandler(userRepository))
    val deleteUserAction = DeleteUserAction(DeleteUserHandler(userRepository))
    val updateUserAction = UpdateUserAction(UpdateUserHandler(userRepository))
    val followUserAction = FollowUserAction(FollowUserCommandHandler(userRepository))
    val unfollowUserAction = UnfollowUserAction(UnfollowUserCommandHandler(userRepository))

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

        get("/users/username/{username}") {

            val query = FindUserByUsernameQuery(call.parameters["username"].toString())

            val result = findUserByUsernameAction.execute(query)

            call.respond(HttpStatusCode.OK, result)

        }

        get("/users/email/{email}") {

            val query = FindUserByEmailQuery(call.parameters["email"].toString())

            val result = findUserByEmailAction.execute(query)

            call.respond(HttpStatusCode.OK, result)

        }

        delete("/users/id/{id}"){
            try {
                deleteUserAction.execute(call.parameters)
                call.respond(HttpStatusCode.Accepted, mapOf("message" to "User deleted"))
            }catch (e:Exception){
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
            }

        }

        put("/users/id/{id}"){
            val body = call.receive<UpdateUserCommand>()
            try
            {
                updateUserAction.execute(body)
                call.respond(HttpStatusCode.OK, mapOf("message" to "User updated"))
            }
            catch(e:Exception)
            {
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
            }
        }

        post("/users/{userId}/follow/{targetUserId}") {
            val userId = call.parameters["userId"] ?: return@post call.respond(HttpStatusCode.BadRequest, "User ID is required")
            val targetUserId = call.parameters["targetUserId"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Target User ID is required")

            try {
                followUserAction.execute(userId, targetUserId)
                call.respond(HttpStatusCode.OK, "User followed successfully")
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("message" to "An error occurred"))
            }
        }

        post("/users/{userId}/unfollow/{targetUserId}") {
            val userId = call.parameters["userId"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Missing userId")
            val targetUserId = call.parameters["targetUserId"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Missing targetUserId")

            try {
                unfollowUserAction.execute(userId, targetUserId)
                call.respond(HttpStatusCode.OK, mapOf("message" to "User unfollowed successfully"))
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("message" to "An error occurred"))
            }
        }

    }
}