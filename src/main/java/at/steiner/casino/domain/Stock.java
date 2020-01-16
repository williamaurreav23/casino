package at.steiner.casino.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Stock.
 */
@Entity
@Table(name = "stock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @Column(name = "value")
    private Integer value;

    @OneToMany(mappedBy = "stock")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlayerStockTransaction> playerStockTransactions = new HashSet<>();

    @OneToMany(mappedBy = "stock")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlayerStock> playerStocks = new HashSet<>();

    @OneToMany(mappedBy = "stock")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StockValueChange> stockValueChanges = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Stock name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public Stock color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getValue() {
        return value;
    }

    public Stock value(Integer value) {
        this.value = value;
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Set<PlayerStockTransaction> getPlayerStockTransactions() {
        return playerStockTransactions;
    }

    public Stock playerStockTransactions(Set<PlayerStockTransaction> playerStockTransactions) {
        this.playerStockTransactions = playerStockTransactions;
        return this;
    }

    public Stock addPlayerStockTransaction(PlayerStockTransaction playerStockTransaction) {
        this.playerStockTransactions.add(playerStockTransaction);
        playerStockTransaction.setStock(this);
        return this;
    }

    public Stock removePlayerStockTransaction(PlayerStockTransaction playerStockTransaction) {
        this.playerStockTransactions.remove(playerStockTransaction);
        playerStockTransaction.setStock(null);
        return this;
    }

    public void setPlayerStockTransactions(Set<PlayerStockTransaction> playerStockTransactions) {
        this.playerStockTransactions = playerStockTransactions;
    }

    public Set<PlayerStock> getPlayerStocks() {
        return playerStocks;
    }

    public Stock playerStocks(Set<PlayerStock> playerStocks) {
        this.playerStocks = playerStocks;
        return this;
    }

    public Stock addPlayerStock(PlayerStock playerStock) {
        this.playerStocks.add(playerStock);
        playerStock.setStock(this);
        return this;
    }

    public Stock removePlayerStock(PlayerStock playerStock) {
        this.playerStocks.remove(playerStock);
        playerStock.setStock(null);
        return this;
    }

    public void setPlayerStocks(Set<PlayerStock> playerStocks) {
        this.playerStocks = playerStocks;
    }

    public Set<StockValueChange> getStockValueChanges() {
        return stockValueChanges;
    }

    public Stock stockValueChanges(Set<StockValueChange> stockValueChanges) {
        this.stockValueChanges = stockValueChanges;
        return this;
    }

    public Stock addStockValueChange(StockValueChange stockValueChange) {
        this.stockValueChanges.add(stockValueChange);
        stockValueChange.setStock(this);
        return this;
    }

    public Stock removeStockValueChange(StockValueChange stockValueChange) {
        this.stockValueChanges.remove(stockValueChange);
        stockValueChange.setStock(null);
        return this;
    }

    public void setStockValueChanges(Set<StockValueChange> stockValueChanges) {
        this.stockValueChanges = stockValueChanges;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stock)) {
            return false;
        }
        return id != null && id.equals(((Stock) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Stock{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", color='" + getColor() + "'" +
            ", value=" + getValue() +
            "}";
    }
}
