package utn.methodology

data class User(val id: String, val name: String, val email: String)

class UserService {

    fun createUser(user: User): Boolean {
        validateUser(user)
        // Aquí iría la lógica para guardar el usuario en la base de datos
        return true
    }

    private fun validateUser(user: User) {
        if (user.id.isBlank() || user.name.isBlank() || !isValidEmail(user.email)) {
            throw IllegalArgumentException("Invalid user data")
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }
}
