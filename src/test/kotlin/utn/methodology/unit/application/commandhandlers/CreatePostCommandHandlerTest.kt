package utn.methodology.unit.application.commandhandlers

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import utn.methodology.application.commandhandlers.CreatePostCommandHandler
import utn.methodology.application.commands.CreatePostCommand
import utn.methodology.infrastructure.persistence.repositories.PostRepository

class CreatePostCommandHandlerTest {

    private val postRepository: PostRepository = mockk(relaxed = true)
    private val createPostHandler = CreatePostCommandHandler(postRepository)

    @Test
    fun create_post_should_return_201() {
        val command = CreatePostCommand("UIJAHDUIHFUI473683", "test message")
        createPostHandler.handle(command)

        verify { postRepository.save(any()) }
    }

    @Test
    fun create_post_should_return_400() {
        val invalidCommand = CreatePostCommand("", "")

        assertThrows(IllegalArgumentException::class.java) {
            createPostHandler.handle(invalidCommand)
        }
    }

    @Test
    fun create_post_should_return_400_when_message_is_empty() {
        val command = CreatePostCommand("UIJAHDUIHFUI473683", "")

        assertThrows(IllegalArgumentException::class.java) {
            createPostHandler.handle(command)
        }
    }

    @Test
    fun create_post_should_return_400_when_userId_is_empty() {
        val command = CreatePostCommand("", "test message")

        assertThrows(IllegalArgumentException::class.java) {
            createPostHandler.handle(command)
        }
    }
}
