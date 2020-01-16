package at.steiner.casino.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link at.steiner.casino.domain.PlayerStock} entity.
 */
public class PlayerStockDTO implements Serializable {

    private Long id;

    private Integer amount;


    private Long playerId;

    private Long stockId;

    private String stockName;

    private String stockColor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockColor() {
        return stockColor;
    }

    public void setStockColor(String stockColor) {
        this.stockColor = stockColor;
    }

    public PlayerStockDTO() {
    }

    public PlayerStockDTO(Integer amount, Long playerId, StockDTO stock) {
        this.amount = amount;
        this.playerId = playerId;
        this.stockId = stock.getId();
        this.stockName = stock.getName();
        this.stockColor = stock.getColor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlayerStockDTO playerStockDTO = (PlayerStockDTO) o;
        if (playerStockDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), playerStockDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlayerStockDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", playerId=" + getPlayerId() +
            ", stockId=" + getStockId() +
            "}";
    }
}
