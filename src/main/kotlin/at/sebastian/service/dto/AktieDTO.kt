package at.sebastian.service.dto

import java.io.Serializable

/**
 * A DTO for the [at.sebastian.domain.Aktie] entity.
 */
data class AktieDTO(

    var id: Long? = null,

    var name: String? = null

) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AktieDTO) return false
        if (other.id == null || id == null) return false

        return id == other.id
    }

    override fun hashCode() = id.hashCode()
}
