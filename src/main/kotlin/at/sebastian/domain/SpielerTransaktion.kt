package at.sebastian.domain

import at.sebastian.domain.enumeration.Transaktion
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy

/**
 * A SpielerTransaktion.
 */
@Entity
@Table(name = "spieler_transaktion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class SpielerTransaktion(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    var id: Long? = null,

    @Column(name = "wert")
    var wert: Int? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "typ")
    var typ: Transaktion? = null,

    @ManyToOne
    var spieler: Spieler? = null

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
) : Serializable {
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SpielerTransaktion) return false
        if (other.id == null || id == null) return false

        return id == other.id
    }

    override fun hashCode() = 31

    override fun toString() = "SpielerTransaktion{" +
        "id=$id" +
        ", wert=$wert" +
        ", typ='$typ'" +
        "}"

    companion object {
        private const val serialVersionUID = 1L
    }
}
