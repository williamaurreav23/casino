package at.sebastian.domain

import java.io.Serializable
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy

/**
 * A SpielerAktieHistory.
 */
@Entity
@Table(name = "spieler_aktie_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class SpielerAktieHistory(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    var id: Long? = null,

    @Column(name = "anzahl")
    var anzahl: Int? = null,

    @Column(name = "creation_time")
    var creationTime: Instant? = null,

    @ManyToOne
    var spieler: Spieler? = null,

    @ManyToOne
    var aktie: Aktie? = null

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
) : Serializable {
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SpielerAktieHistory) return false
        if (other.id == null || id == null) return false

        return id == other.id
    }

    override fun hashCode() = 31

    override fun toString() = "SpielerAktieHistory{" +
        "id=$id" +
        ", anzahl=$anzahl" +
        ", creationTime='$creationTime'" +
        "}"

    companion object {
        private const val serialVersionUID = 1L
    }
}
