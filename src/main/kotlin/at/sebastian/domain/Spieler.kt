package at.sebastian.domain

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table

import java.io.Serializable

/**
 * A Spieler.
 */
@Entity
@Table(name = "spieler")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class Spieler(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    var id: Long? = null,

    @Column(name = "vorname")
    var vorname: String? = null,

    @Column(name = "nachname")
    var nachname: String? = null,

    @Column(name = "is_kind")
    var isKind: Boolean? = null,

    @Column(name = "kennzahl")
    var kennzahl: Int? = null,

    @OneToMany(mappedBy = "spieler")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var spielerAkties: MutableSet<SpielerAktie> = mutableSetOf(),

    @OneToMany(mappedBy = "spieler")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var spielerTransaktions: MutableSet<SpielerTransaktion> = mutableSetOf()

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
) : Serializable {

    fun addSpielerAktie(spielerAktie: SpielerAktie): Spieler {
        this.spielerAkties.add(spielerAktie)
        spielerAktie.spieler = this
        return this
    }

    fun removeSpielerAktie(spielerAktie: SpielerAktie): Spieler {
        this.spielerAkties.remove(spielerAktie)
        spielerAktie.spieler = null
        return this
    }

    fun addSpielerTransaktion(spielerTransaktion: SpielerTransaktion): Spieler {
        this.spielerTransaktions.add(spielerTransaktion)
        spielerTransaktion.spieler = this
        return this
    }

    fun removeSpielerTransaktion(spielerTransaktion: SpielerTransaktion): Spieler {
        this.spielerTransaktions.remove(spielerTransaktion)
        spielerTransaktion.spieler = null
        return this
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Spieler) return false
        if (other.id == null || id == null) return false

        return id == other.id
    }

    override fun hashCode() = 31

    override fun toString() = "Spieler{" +
        "id=$id" +
        ", vorname='$vorname'" +
        ", nachname='$nachname'" +
        ", isKind='$isKind'" +
        ", kennzahl=$kennzahl" +
        "}"


    companion object {
        private const val serialVersionUID = 1L
    }
}
