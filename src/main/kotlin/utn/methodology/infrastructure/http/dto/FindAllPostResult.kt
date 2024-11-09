package utn.methodology.infrastructure.http.dto

import kotlinx.serialization.Serializable
import utn.methodology.domain.entities.Post

@Serializable
data class FindAllPostResult(val posts: List<Map<String, String>>) {
}