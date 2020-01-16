package at.steiner.casino.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_child")
    private Boolean isChild;

    @Column(name = "pass_number")
    private Integer passNumber;

    @Column(name = "money")
    private Integer money;

    @OneToMany(mappedBy = "player")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlayerMoneyTransaction> playerMoneyTransactions = new HashSet<>();

    @OneToMany(mappedBy = "player")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlayerStockTransaction> playerStockTransactions = new HashSet<>();

    @OneToMany(mappedBy = "player")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlayerStock> playerStocks = new HashSet<>();

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

    public Player name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsChild() {
        return isChild;
    }

    public Player isChild(Boolean isChild) {
        this.isChild = isChild;
        return this;
    }

    public void setIsChild(Boolean isChild) {
        this.isChild = isChild;
    }

    public Integer getPassNumber() {
        return passNumber;
    }

    public Player passNumber(Integer passNumber) {
        this.passNumber = passNumber;
        return this;
    }

    public void setPassNumber(Integer passNumber) {
        this.passNumber = passNumber;
    }

    public Integer getMoney() {
        return money;
    }

    public Player money(Integer money) {
        this.money = money;
        return this;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Set<PlayerMoneyTransaction> getPlayerMoneyTransactions() {
        return playerMoneyTransactions;
    }

    public Player playerMoneyTransactions(Set<PlayerMoneyTransaction> playerMoneyTransactions) {
        this.playerMoneyTransactions = playerMoneyTransactions;
        return this;
    }

    public Player addPlayerMoneyTransaction(PlayerMoneyTransaction playerMoneyTransaction) {
        this.playerMoneyTransactions.add(playerMoneyTransaction);
        playerMoneyTransaction.setPlayer(this);
        return this;
    }

    public Player removePlayerMoneyTransaction(PlayerMoneyTransaction playerMoneyTransaction) {
        this.playerMoneyTransactions.remove(playerMoneyTransaction);
        playerMoneyTransaction.setPlayer(null);
        return this;
    }

    public void setPlayerMoneyTransactions(Set<PlayerMoneyTransaction> playerMoneyTransactions) {
        this.playerMoneyTransactions = playerMoneyTransactions;
    }

    public Set<PlayerStockTransaction> getPlayerStockTransactions() {
        return playerStockTransactions;
    }

    public Player playerStockTransactions(Set<PlayerStockTransaction> playerStockTransactions) {
        this.playerStockTransactions = playerStockTransactions;
        return this;
    }

    public Player addPlayerStockTransaction(PlayerStockTransaction playerStockTransaction) {
        this.playerStockTransactions.add(playerStockTransaction);
        playerStockTransaction.setPlayer(this);
        return this;
    }

    public Player removePlayerStockTransaction(PlayerStockTransaction playerStockTransaction) {
        this.playerStockTransactions.remove(playerStockTransaction);
        playerStockTransaction.setPlayer(null);
        return this;
    }

    public void setPlayerStockTransactions(Set<PlayerStockTransaction> playerStockTransactions) {
        this.playerStockTransactions = playerStockTransactions;
    }

    public Set<PlayerStock> getPlayerStocks() {
        return playerStocks;
    }

    public Player playerStocks(Set<PlayerStock> playerStocks) {
        this.playerStocks = playerStocks;
        return this;
    }

    public Player addPlayerStock(PlayerStock playerStock) {
        this.playerStocks.add(playerStock);
        playerStock.setPlayer(this);
        return this;
    }

    public Player removePlayerStock(PlayerStock playerStock) {
        this.playerStocks.remove(playerStock);
        playerStock.setPlayer(null);
        return this;
    }

    public void setPlayerStocks(Set<PlayerStock> playerStocks) {
        this.playerStocks = playerStocks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        return id != null && id.equals(((Player) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Player{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isChild='" + isIsChild() + "'" +
            ", passNumber=" + getPassNumber() +
            ", money=" + getMoney() +
            "}";
    }
}
