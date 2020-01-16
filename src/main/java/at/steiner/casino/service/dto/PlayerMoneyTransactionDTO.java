package at.steiner.casino.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import at.steiner.casino.domain.enumeration.Transaction;

/**
 * A DTO for the {@link at.steiner.casino.domain.PlayerMoneyTransaction} entity.
 */
public class PlayerMoneyTransactionDTO implements Serializable {

    private Long id;

    private Instant time;

    private Transaction transaction;

    private Integer value;


    private Long playerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlayerMoneyTransactionDTO playerMoneyTransactionDTO = (PlayerMoneyTransactionDTO) o;
        if (playerMoneyTransactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), playerMoneyTransactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlayerMoneyTransactionDTO{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", transaction='" + getTransaction() + "'" +
            ", value=" + getValue() +
            ", playerId=" + getPlayerId() +
            "}";
    }
}
