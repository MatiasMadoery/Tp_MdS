package utn.methodology.application.commands

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