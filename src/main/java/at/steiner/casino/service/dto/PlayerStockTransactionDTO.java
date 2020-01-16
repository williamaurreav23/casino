package at.steiner.casino.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link at.steiner.casino.domain.PlayerStockTransaction} entity.
 */
public class PlayerStockTransactionDTO implements Serializable {

    private Long id;

    private Instant time;

    private Integer amount;


    private Long playerId;

    private Long stockId;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlayerStockTransactionDTO playerStockTransactionDTO = (PlayerStockTransactionDTO) o;
        if (playerStockTransactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), playerStockTransactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlayerStockTransactionDTO{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", amount=" + getAmount() +
            ", playerId=" + getPlayerId() +
            ", stockId=" + getStockId() +
            "}";
    }
}
