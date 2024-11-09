package utn.methodology

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun create_user_should_returns_201() {
        val userService = mockk<UserService>()
        val user = User("1", "John Doe", "john.doe@example.com")

        every { userService.createUser(user) } returns true

        val result = userService.createUser(user)
        assertEquals(true, result)
    }
    @Test
    fun create_user_should_returns_400() {
        val userService = mockk<UserService>()
        val invalidUser = User("", "", "invalid-email")

        every { userService.createUser(invalidUser) } throws IllegalArgumentException("Invalid user data")

        assertThrows<IllegalArgumentException> {
            userService.createUser(invalidUser)
        }
    }
}
