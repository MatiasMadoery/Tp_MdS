package utn.methodology.application.commands

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserCommand(
    val id: String,
    val name: String,
    val username: String,
    val email: String,
    val password: String
) {
    fun validate(): UpdateUserCommand{
        checkNotNull(id){throw IllegalArgumentException("Id must be defined")}
        checkNotNull(name){throw IllegalArgumentException("Name must be defined")}
        checkNotNull(username){throw IllegalArgumentException("User Name must be defined")}
        checkNotNull(email){throw  IllegalArgumentException("Email must be defined")}
        checkNotNull(password){throw  IllegalArgumentException("Password must be defined")}
        return this
    }
}