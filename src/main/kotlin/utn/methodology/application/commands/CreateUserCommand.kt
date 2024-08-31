package utn.methodology.application.commands

import java.util.Scanner

data class CreateUserCommand(
    val name: String,
    val userName: String,
    val email: String,
    val password: String
) {
    fun validate(): CreateUserCommand{
        checkNotNull(name){throw IllegalArgumentException("Name must be defined")}
        checkNotNull(userName){throw IllegalArgumentException("User Name must be defined")}
        checkNotNull(email){throw  IllegalArgumentException("Email must be defined")}
        checkNotNull(password){throw  IllegalArgumentException("Password must be defined")}

        return this;
    }
}

