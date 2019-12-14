package at.sebastian.service.dto

import at.sebastian.domain.enumeration.Transaktion
import java.io.Serializable

/**
 * A DTO for the [at.sebastian.domain.SpielerTransaktion] entity.
 */
data class SpielerTransaktionDTO(

    var id: Long? = null,

    var wert: Int? = null,

    var typ: Transaktion? = null,

    var spielerId: Long? = null

) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SpielerTransaktionDTO) return false
        if (other.id == null || id == null) return false

        return id == other.id
    }

    override fun hashCode() = id.hashCode()
}
