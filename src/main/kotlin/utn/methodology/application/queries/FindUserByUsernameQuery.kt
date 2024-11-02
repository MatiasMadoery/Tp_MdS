package utn.methodology.application.queries

data class FindUserByUsernameQuery(val username: String)
{

    fun validate(): FindUserByUsernameQuery {
        require(username.isNotBlank()) { throw IllegalArgumentException("Id must be defined and cannot be blank") }// verificamos que no se ingresen espacios en blanco o solo espacios
        return this
    }

}
