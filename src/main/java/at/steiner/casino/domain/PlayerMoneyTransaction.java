package at.steiner.casino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import at.steiner.casino.domain.enumeration.Transaction;

/**
 * A PlayerMoneyTransaction.
 */
@Entity
@Table(name = "player_money_transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlayerMoneyTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "time")
    private Instant time;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction")
    private Transaction transaction;

    @Column(name = "value")
    private Integer value;

    @ManyToOne
    @JsonIgnoreProperties("playerMoneyTransactions")
    private Player player;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTime() {
        return time;
    }

    public PlayerMoneyTransaction time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public PlayerMoneyTransaction transaction(Transaction transaction) {
        this.transaction = transaction;
        return this;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Integer getValue() {
        return value;
    }

    public PlayerMoneyTransaction value(Integer value) {
        this.value = value;
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerMoneyTransaction player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlayerMoneyTransaction)) {
            return false;
        }
        return id != null && id.equals(((PlayerMoneyTransaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PlayerMoneyTransaction{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", transaction='" + getTransaction() + "'" +
            ", value=" + getValue() +
            "}";
    }
}
