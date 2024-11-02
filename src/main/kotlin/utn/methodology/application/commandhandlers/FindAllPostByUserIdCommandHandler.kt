package utn.methodology.application.commandhandlers

import utn.methodology.application.commands.FindAllPostsByUserIdCommand
import utn.methodology.domain.entities.Post
import utn.methodology.infrastructure.persistence.repositories.PostRepository

class FindAllPostsByUserIdHandler(private val postRepository: PostRepository) {
    fun handle(command: FindAllPostsByUserIdCommand): List<Post> {
        return postRepository.findAllByUserId(command.userId, command.order, command.limit, command.offset)
    }
}
