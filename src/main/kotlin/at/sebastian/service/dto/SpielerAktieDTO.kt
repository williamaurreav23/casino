package at.sebastian.service.dto

import java.io.Serializable

/**
 * A DTO for the [at.sebastian.domain.SpielerAktie] entity.
 */
data class SpielerAktieDTO(

    var id: Long? = null,

    var anzahl: Int? = null,

    var spielerId: Long? = null,

    var aktieId: Long? = null

) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SpielerAktieDTO) return false
        if (other.id == null || id == null) return false

        return id == other.id
    }

    override fun hashCode() = id.hashCode()
}
