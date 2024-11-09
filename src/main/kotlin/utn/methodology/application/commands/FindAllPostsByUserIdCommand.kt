package utn.methodology.application.commands

data class FindAllPostsByUserIdCommand(
    val userId: String,
    val order: String? = null,
    val limit: Int? = null,
    val offset: Int? = null
)