package utn.methodology.infrastructure.persistence.repositories
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.bson.Document
import org.bson.types.ObjectId
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import java.util.logging.Filter

class RepositorioUsuario(private val database:MongoDatabase) : RepositorioUsuario {
    private val coleccion = database.getCollection<Usuario>("Usuarios")

    fun Guardar (Usuario: usuario): Usuario {
        coleccion.insertOne(usuario)
        return usuario
    }

    fun BuscarPorId(id: Long): Usuario? {
        val idUsuario = ObjectId(id.toString())
        return coleccion.find(Document("id",idUsuario)).firstOrNull()
    }

    fun Actualizar(Usuario: usuario): Usuario{
        val filtro = Filters.eq("id",id)

        val actualizacion = Updates.combine(
            Updates.set("name",usuario.name),
            Updates.set("username",usuario.username),
            Updates.set("email",usuario.email),
            Updates.set("password",usuario.password)
        )

        coleccion.updateOne(filtro,actualizacion)

        return usuario
    }

    fun ExistePorUsername(username: String): Boolean{
        val filtro = Filters.eq("username",username)

        val resultado = coleccion.find(filtro).firstOrNull()

        return resultado != null
    }


    fun ExistePorEmail(email: String): Boolean{
        val filtro = Filters.eq("email",email)

        val resultado = coleccion.find(filtro).firstOrNull()

        return resultado != null
    }
}