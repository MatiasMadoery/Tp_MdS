package utn.methodology.unit.application.commandhandlers

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import utn.methodology.application.commandhandlers.CreateUserHandler
import utn.methodology.application.commands.CreateUserCommand
import utn.methodology.infrastructure.persistence.repositories.UserRepository

class CreateUserCommandHandlerTest {

    private val userRepository: UserRepository = mockk(relaxed = true)
    private val createUserHandler = CreateUserHandler(userRepository)

    @Test
    fun create_user_should_return_201() {
        val command = CreateUserCommand("Matias", "gari862", "matiascabrera2725@gmail.com", "password123")
        createUserHandler.handle(command)

        verify { userRepository.save(any()) }
    }

    @Test
    fun create_user_should_return_400() {
        val invalidCommand = CreateUserCommand("", "", "", "")
        assertThrows(IllegalArgumentException::class.java) {
            createUserHandler.handle(invalidCommand)
        }
    }

    @Test
    fun create_user_should_return_400_without_name() {
        val command = CreateUserCommand("", "gari862", "matiascabrera2725@gmail.com", "password123")
        assertThrows(IllegalArgumentException::class.java) {
            createUserHandler.handle(command)
        }
    }

    @Test
    fun create_user_should_return_400_without_username() {
        val command = CreateUserCommand("Matias", "", "matiascabrera2725@gmail.com", "password123")
        assertThrows(IllegalArgumentException::class.java) {
            createUserHandler.handle(command)
        }
    }

    @Test
    fun create_user_should_return_400_without_email() {
        val command = CreateUserCommand("Matias", "gari862", "", "password123")
        assertThrows(IllegalArgumentException::class.java) {
            createUserHandler.handle(command)
        }
    }

    @Test
    fun create_user_should_return_400_without_password() {
        val command = CreateUserCommand("Matias", "gari862", "matiascabrera2725@gmail.com", "")
        assertThrows(IllegalArgumentException::class.java) {
            createUserHandler.handle(command)
        }
    }
}
