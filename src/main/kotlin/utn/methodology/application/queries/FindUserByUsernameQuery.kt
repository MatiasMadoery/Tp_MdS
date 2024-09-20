package utn.methodology.application.queries

data class FindUserByUsernameQuery(val Username: String)
{

    fun validate(): FindUserByUsernameQuery {
        require(Username.isNotBlank()) { throw IllegalArgumentException("Id must be defined and cannot be blank") }// verificamos que no se ingresen espacios en blanco o solo espacios
        return this
    }

}
