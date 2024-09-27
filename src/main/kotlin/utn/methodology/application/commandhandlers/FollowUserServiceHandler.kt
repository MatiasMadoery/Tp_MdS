package utn.methodology.application.commandhandlers

class FollowUserServiceHandler {

    data class FollowUserCommand(
        val  followerId: Long,
        val followingId: Long
    )

    sealed class Result{
        data class Success (val message: String) : Result()
        data class Error (val error: String):Result()
    }

    data class User(
        val id: Long,
        val name: String,
        var followersCount: Int = 0,
        var followingCount: Int = 0
    )

    data class FollowRelation(
        val followerId: Long,
        val followingId: Long
    )

    interface UserRepository{
        fun findById(userId: Long) : User?
        fun incrementFollowerCount(userId: Long)
        fun incrementFollowingCount(userId: Long)
    }

    interface FollowRepository{
        fun findFollowRelation(followerId: Long, followingId: Long) : FollowRelation?
        fun createFollow(followerId: Long, followingId: Long)
    }

    class InMemoryUserRepository : UserRepository {
        private val users = mutableMapOf<Long, User>()

        override fun findById(userId: Long): User? {
            return users[userId]
        }

        override fun incrementFollowerCount(userId: Long) {
            users[userId]?.let { it.followersCount++ }
        }

        override fun incrementFollowingCount(userId: Long) {
            users[userId]?.let { it.followingCount++ }
        }
    }


    class InMemoryFollowRepository : FollowRepository{
        private val followRelations = mutableSetOf<FollowRelation>()

        override fun findFollowRelation(followerId: Long, followingId: Long) : FollowRelation?{
            return  followRelations.find { it.followerId == followerId && it.followingId == followingId}
        }

        override fun createFollow(followerId: Long, followingId: Long){
            followRelations.add(FollowRelation(followerId, followingId))
        }
    }

    class FollowUserCommandHandler(
        private val userRepository: UserRepository,
        private val followRepository: FollowRepository
    ){
        fun handle(command: FollowUserCommand): Result {
            val follower = userRepository.findById(command.followerId)
            val following = userRepository.findById(command.followingId)

            if (follower == null || following == null) {
                return Result.Error("One or both users do not exist")
            }

            if (command.followerId == command.followingId) {
                return Result.Error("You can't follow yourself")
            }

            val existingfolow = followRepository.findFollowRelation(command.followerId, command.followingId)
            if (existingfolow != null) {
                return Result.Error("You already follow this user")
            }

            followRepository.createFollow(command.followerId, command.followerId)

            userRepository.incrementFollowerCount(command.followingId)
            userRepository.incrementFollowingCount(command.followerId)

            return Result.Success("Now you follow ${following.name}")
        }
    }

    fun run(){
        val userRepository = InMemoryUserRepository()
        val followRepository = InMemoryFollowRepository()

        val commandHandler = FollowUserCommandHandler(userRepository, followRepository)

        val followUserCommand = FollowUserCommand(followerId = 1L, followingId = 2L)

        val result = commandHandler.handle(followUserCommand)

        when(result){
            is Result.Success -> println(result.message)
            is Result.Error -> println("Error: ${result.error}")
        }
    }
}

fun main(){
    val service = FollowUserServiceHandler()
    service.run()
}