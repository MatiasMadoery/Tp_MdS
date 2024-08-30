package kotlin.utn.methodology.infrastructure.http.actions

class CreateUserAction(private val handler: CreateUserHandler) {
    fun execute(body: CreateuserCommand) {
        // Valida los datos del comando
        body.validate().let {
            // Si los datos son válidos, pasa el comando al handler
            handler.handle(body)
        }
    }
}

