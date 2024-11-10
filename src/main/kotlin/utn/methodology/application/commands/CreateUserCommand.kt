package utn.methodology.application.commands

import kotlinx.serialization.Serializable


@Serializable()
data class CreateUserCommand(
    val name: String,
    val username: String,
    val email: String,
    val password: String
) {
    fun validate(): CreateUserCommand {
        require(name.isNotBlank()) { "Name must be defined" }
        require(username.isNotBlank()) { "User Name must be defined" }
        require(email.isNotBlank()) { "Email must be defined" }
        require(password.isNotBlank()) { "Password must be defined" }
        return this
    }
}


