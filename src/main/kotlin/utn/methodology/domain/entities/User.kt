package utn.methodology.domain.entities
import ch.qos.logback.core.status.Status
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer
import java.util.UUID


data class User (
    val id : Int,
    val name : String,
    val username : String,
    val email : String,
    val password : String
)


