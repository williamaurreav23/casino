package at.sebastian.service.dto

import java.io.Serializable

/**
 * A DTO for the [at.sebastian.domain.AktieWert] entity.
 */
data class AktieWertDTO(

    var id: Long? = null,

    var wert: Int? = null,

    var aktieId: Long? = null

) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AktieWertDTO) return false
        if (other.id == null || id == null) return false

        return id == other.id
    }

    override fun hashCode() = id.hashCode()
}
