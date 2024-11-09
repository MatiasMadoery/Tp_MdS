package utn.methodology.application.commandhandlers

import utn.methodology.application.commands.FindAllPostsByUserIdCommand
import utn.methodology.application.commands.FindPostsByFollowedUserIdsCommand
import utn.methodology.domain.entities.Post
import utn.methodology.infrastructure.persistence.repositories.PostRepository
import utn.methodology.infrastructure.persistence.repositories.UserRepository

data class FindPostsByFollowedUserIdsCommandHandler(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
){
    fun execute(command: FindPostsByFollowedUserIdsCommand) : List<Post>{
        val followingUserIds = userRepository.findById(command.userId)?.getFollowing()
            ?: return emptyList()

        return postRepository.findPostsByFollowedUserIds(
            followedUserIds = followingUserIds,
            order = command.order,
            limit = command.limit,
            offset = command.offset
        )
    }
}
