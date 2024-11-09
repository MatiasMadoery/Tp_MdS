package utn.methodology.application.commandhandlers

import utn.methodology.application.commands.CreatePostCommand
import utn.methodology.domain.entities.Post
import utn.methodology.infrastructure.persistence.repositories.PostRepository
import java.time.LocalDateTime

class CreatePostCommandHandler (private val postRepository: PostRepository) {
    fun handle(command: CreatePostCommand) {

        println(command)

        val post = Post(
            idUser = command.idUser,
            message = command.message,
            date = LocalDateTime.now().toString()
        )
        postRepository.save(post)
    }
}