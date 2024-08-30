package utn.methodology.application.commandhandlers

class ConfirmacionHandler (
            private val repositorioUsuario: RepositorioUsuario,
            private val eventoBus: EventBus,
    )
{
    fun handle(command: ConfirmacionComando)
    {
        val usuario = Usuario.create(
            command.Id,
            command.Name,
            command.Username,
            command.Email,
            command.Passgord,
        )

        repositorioUsuario.save(usuario)
        eventoBus.publish(usuario.pullDomainEvents())
    }

}

