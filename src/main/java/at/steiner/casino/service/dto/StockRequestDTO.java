package at.steiner.casino.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for requesting the {@link at.steiner.casino.domain.PlayerStockTransaction} entity.
 */
public class StockRequestDTO implements Serializable {

    private Long stockId;

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockRequestDTO that = (StockRequestDTO) o;
        return Objects.equals(stockId, that.stockId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockId);
    }

    @Override
    public String toString() {
        return "StockRequestDTO{" +
            "stockId=" + stockId +
            '}';
    }
}
