package utn.methodology.application.commands

data class FindPostsByFollowedUserIdsCommand(
    val userId: String,
    val order: String? = null,
    val limit: Int? = null,
    val offset: Int? = null
)
