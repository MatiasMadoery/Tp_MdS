package utn.methodology.application.commandhandlers

import utn.methodology.application.commands.DeletePostCommand
import utn.methodology.infrastructure.persistence.repositories.PostRepository

class DeletePostCommandHandler (private val postRepository: PostRepository) {
    fun handle(command: DeletePostCommand) {
        postRepository.delete(command.postId)
    }
}