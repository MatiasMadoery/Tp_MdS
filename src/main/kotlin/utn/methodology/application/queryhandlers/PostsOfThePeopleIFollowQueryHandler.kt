package utn.methodology.application.queryhandlers

import utn.methodology.application.queries.PostsOfThePeopleIFollowQuery
import utn.methodology.domain.entities.Post
import utn.methodology.domain.entities.User
import utn.methodology.infrastructure.persistence.repositories.PostRepository
import utn.methodology.infrastructure.persistence.repositories.UserRepository

class PostsOfThePeopleIFollowQueryHandler (private val postRepository: PostRepository, private val userRepository: UserRepository)
{
    fun handle(query: PostsOfThePeopleIFollowQuery) : List<Post> {
        val idsFollowers = userRepository.findFollowedIdsByUserId(query.UserId)

        return postRepository.findPostsByUserIds(idsFollowers)
    }

}
