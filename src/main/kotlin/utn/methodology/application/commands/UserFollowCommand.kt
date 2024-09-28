package utn.methodology.application.commands

import com.mongodb.internal.connection.CommandMessage
import utn.methodology.infrastructure.persistence.repositories.UserRepository

 data class UserFollowCommand (
    val followerId : String,
    val followedId : String
){
    fun validate(): UserFollowCommand{
        checkNotNull(followerId){throw IllegalArgumentException("The user does not exist")}
        checkNotNull(followedId){throw IllegalArgumentException("The user does not exist")}

        return this
    }
 }