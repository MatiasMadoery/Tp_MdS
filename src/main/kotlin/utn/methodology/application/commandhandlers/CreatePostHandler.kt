package utn.methodology.application.commandhandlers
import utn.methodology.application.commands.CreatePostCommand
import utn.methodology.domain.entities.Post
import utn.methodology.infrastructure.persistence.repositories.PostRepository

class CreatePostHandler (
    private val postRepository: PostRepository,
    )
{
    fun handle(command: CreatePostCommand){
        val post = Post(
            postId = command.postId,
            userId = command.userId,
            message = command.message,
            date = command.date
        )

        postRepository.savePost(post)
    }

}