package at.sebastian.domain

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy

/**
 * A Aktie.
 */
@Entity
@Table(name = "aktie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class Aktie(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    var id: Long? = null,

    @Column(name = "name")
    var name: String? = null,

    @OneToMany(mappedBy = "aktie")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var spielerAkties: MutableSet<SpielerAktie> = mutableSetOf(),

    @OneToMany(mappedBy = "aktie")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var aktieWerts: MutableSet<AktieWert> = mutableSetOf()

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
) : Serializable {

    fun addSpielerAktie(spielerAktie: SpielerAktie): Aktie {
        this.spielerAkties.add(spielerAktie)
        spielerAktie.aktie = this
        return this
    }

    fun removeSpielerAktie(spielerAktie: SpielerAktie): Aktie {
        this.spielerAkties.remove(spielerAktie)
        spielerAktie.aktie = null
        return this
    }

    fun addAktieWert(aktieWert: AktieWert): Aktie {
        this.aktieWerts.add(aktieWert)
        aktieWert.aktie = this
        return this
    }

    fun removeAktieWert(aktieWert: AktieWert): Aktie {
        this.aktieWerts.remove(aktieWert)
        aktieWert.aktie = null
        return this
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Aktie) return false
        if (other.id == null || id == null) return false

        return id == other.id
    }

    override fun hashCode() = 31

    override fun toString() = "Aktie{" +
        "id=$id" +
        ", name='$name'" +
        "}"

    companion object {
        private const val serialVersionUID = 1L
    }
}
