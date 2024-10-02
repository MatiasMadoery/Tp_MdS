package utn.methodology.application.commandhandlers

import utn.methodology.application.commands.DeletePostCommand
import utn.methodology.infrastructure.persistence.repositories.PostRepository

class DeletePostCommandHandler(private val postRepository: PostRepository) {
    suspend fun handle(command: DeletePostCommand): Result<String> {
        val post = postRepository.findPostById(command.postId)
        if (post == null) {
            return Result.failure(Exception("Post not found"))
        }

        if (post.getUserId() != command.userId) {
            return Result.failure(Exception("Unauthorized to delete this post"))
        }

        val success = postRepository.deletePostById(command.postId)
        return if (success) {
            Result.success("Post deleted successfully")
        } else {
            Result.failure(Exception("Failed to delete post"))
        }
    }
}