package utn.methodology.application.commands

import java.util.Scanner

class User(
    val userName: String,
    val email: String,
    val password: String
) {
    init {
        require(userName.isNotBlank()) { "El nombre de usuario no puede estar vacio" }
        require(email.isNotBlank() && email.contains("@")) { "El email debe ser valido" }
        require(password.length >= 6) { "La contraseña debe de ser de 6 caracteres minimo" }
    }
}

class CreateUserHandler{
    fun handle(command: User){

        println("Usuario creado: ${command.userName}, ${command.email}, ${command.password}")
    }
}

fun main(){

    val scanner = Scanner(System.`in`)

    try {
        println("Ingrese el nombre de usuario")
        val userName = scanner.nextLine()

        println("Ingrese el email")
        val email = scanner.nextLine()

        println("Ingrese la contraseña")
        val password = scanner.nextLine()

        val command = User(
            userName = userName,
            email = email,
            password = password
        )
        val handler = CreateUserHandler()
        handler.handle(command)

    }catch (e: IllegalArgumentException){
        println("Error al crear el usuario: ${e.message}")
    }
}
