package at.sebastian.service.dto

import java.io.Serializable

/**
 * A DTO for the [at.sebastian.domain.Spieler] entity.
 */
data class SpielerDTO(

    var id: Long? = null,

    var vorname: String? = null,

    var nachname: String? = null,

    var isKind: Boolean? = null,

    var kennzahl: Int? = null

) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SpielerDTO) return false
        if (other.id == null || id == null) return false

        return id == other.id
    }

    override fun hashCode() = id.hashCode()
}
