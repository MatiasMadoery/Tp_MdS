package utn.methodology.application.queries

data class FindUserByEmailQuery(val email: String){
    fun validate(): FindUserByEmailQuery{
        require(email.isNotBlank()){
            throw  IllegalArgumentException("Email must be defined and cannot be blank")
        }
        return this
    }
}
