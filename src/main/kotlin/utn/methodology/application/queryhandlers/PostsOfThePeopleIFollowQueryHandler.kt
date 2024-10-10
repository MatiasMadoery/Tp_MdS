package utn.methodology.application.queryhandlers

import utn.methodology.application.queries.PostsOfThePeopleIFollowQuery
import utn.methodology.infrastructure.persistence.repositories.PostRepository

class PostsOfThePeopleIFollowQueryHandler (private val postRepository: PostRepository) {
    fun handle(query: PostsOfThePeopleIFollowQuery): List<Post> {
        val idsFollowers = postRepository.getIdsTracked(query.UserId)

        return postRepository.getPostsByUsers(idsFollowers)
    }

}