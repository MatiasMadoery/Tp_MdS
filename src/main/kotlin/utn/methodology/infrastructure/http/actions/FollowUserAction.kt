package utn.methodology.infrastructure.http.actions

class FollowUserAction (
    private val followerId: Long,
    private val followedId: Long,
    private val userRepository: UserRepository
){
    fun execute(): ActionResult{

        if(followerId == followerId){
            return ActionResult.Failure("A user cannot follow himself")
        }

        val follower = userRepository.findById(followerId)
            ?: return ActionResult.Failure("Follower user does not exist")

        val followed = userRepository.findById(followedId)
            ?: return ActionResult.Failure("The user to follow does not exist")

        if(follower.isFollowing(followedId)){
            return ActionResult.Failure("You are already following this user")
        }

        follower.follow(followed)
        followed.addFollower(follower)

        return ActionResult.Success("You have started following ${followed.name}")
    }

    sealed class ActionResult{
        data class Success(val message: String) : ActionResult()
        data class Failure(val error: String) : ActionResult()
    }
}

class User(
    val id: Long,
    val name: String,
    private val followedUsers: MutableList<Long> = mutableListOf(),
    private val followers : MutableList<Long> = mutableListOf()
){
    fun isFollowing(userId: Long): Boolean{
        return followedUsers.contains(userId)
    }

    fun follow(user: User){
        followedUsers.add(user.id)
    }

    fun addFollower(user: User){
        followers.add(user.id)
    }
}

interface UserRepository{
    fun findById(userId: Long): User?
    fun save(user: User)
}