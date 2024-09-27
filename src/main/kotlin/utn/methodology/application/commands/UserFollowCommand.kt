package utn.methodology.application.commands

//import com.mongodb.internal.connection.CommandMessage
//import utn.methodology.infrastructure.persistence.repositories.UserRepository

class UserFollowCommand (
    private val followerId : Long,
    private val followedId : Long,
    private val userRepository: UserRepository
){
    fun excecute(): Result{
        if (followerId == followedId){
            return Result.Failure("A user cannot follow himself")
        }

        val follower = userRepository.findById(followerId)
            ?: return Result.Failure("The user does not exist")

        val followed = userRepository.findById(followedId)
            ?: return Result.Failure("The user to follow does not exist")

        if (follower.isFollowing(followedId)){
            return Result.Failure("You are already following this user")
        }

        follower.follow(followed)
        followed.addFollower(follower)

        return Result.Success("You have started following ${followed.name}")
    }
    sealed class Result{
        data class Success(val message: String) : Result()
        data class Failure(val error: String) : Result()
    }
}
class User(
    val id: Long,
    val name: String,
    private val followedUser: MutableList<Long> = mutableListOf(),
    private val followers: MutableList<Long> = mutableListOf()
){
    fun isFollowing(userId: Long): Boolean{
        return followedUser.contains((userId))
    }

    fun follow(user: User){
        followers.add(user.id)
    }

    fun addFollower(user: User){
        followers.add(user.id)
    }
}
interface UserRepository{
    fun findById(userId: Long): User?
    fun save(user: User)
}