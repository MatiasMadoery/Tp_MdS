package utn.methodology.application.queryhandlers

import io.ktor.server.plugins.*
import utn.methodology.application.queries.FindUserByEmailQuery
import utn.methodology.infrastructure.persistence.repositories.UserRepository

class FindUserByEmailHandler(private val userRepository: UserRepository) {
    fun handle(query: FindUserByEmailQuery): Map<String,String>{
        val user = userRepository.existByEmail(query.email)
            ?: throw  NotFoundException("User with email: ${query.email} not found")
        return user.toPrimitives()
    }
}