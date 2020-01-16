package at.steiner.casino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A PlayerStockTransaction.
 */
@Entity
@Table(name = "player_stock_transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlayerStockTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "time")
    private Instant time;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne
    @JsonIgnoreProperties("playerStockTransactions")
    private Player player;

    @ManyToOne
    @JsonIgnoreProperties("playerStockTransactions")
    private Stock stock;

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

    public PlayerStockTransaction time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Integer getAmount() {
        return amount;
    }

    public PlayerStockTransaction amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerStockTransaction player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Stock getStock() {
        return stock;
    }

    public PlayerStockTransaction stock(Stock stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlayerStockTransaction)) {
            return false;
        }
        return id != null && id.equals(((PlayerStockTransaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PlayerStockTransaction{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
