package utn.methodology.application.queryhandlers

import io.ktor.server.plugins.*
import utn.methodology.application.queries.FindUserByUsernameQuery
import utn.methodology.infrastructure.persistence.repositories.UserRepository

class FindUserByUsernameHandler(private val userRepository: UserRepository) {
    fun handle(query: FindUserByUsernameQuery) : Map <String,String> {
        val user = userRepository.existsByUsername(query.Username)
            ?: throw NotFoundException("user with username: ${query.Username} not found")
        return user.toPrimitives()
    }

}