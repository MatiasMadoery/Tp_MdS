package utn.methodology.application.commandhandlers
import io.ktor.server.plugins.*
import utn.methodology.application.commands.CreatePostCommand
import utn.methodology.domain.entities.Post
import utn.methodology.infrastructure.persistence.repositories.PostRepository
import utn.methodology.infrastructure.persistence.repositories.UserRepository
import java.time.LocalDateTime
import java.util.UUID

class CreatePostHandler (
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    )
{
    fun handle(command: CreatePostCommand){
        val userId = this.userRepository.findById(command.userId)

        if (userId == null){
            throw NotFoundException("User not found")
        }
        println("sigo sin andar, no se que onda.")
        println("User $userId")
        println("sigo sin andar, no se que onda.")
        println("sigo sin andar, no se que onda.")
        val post = Post(
            UUID.randomUUID().toString(),
            userId.getId(),
            command.message,
            LocalDateTime.now()
        )

        postRepository.savePost(post)
    }

}