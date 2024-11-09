package utn.methodology.application.commands

data class UnfollowUserCommand(val userId: String, val targetUserId: String)
