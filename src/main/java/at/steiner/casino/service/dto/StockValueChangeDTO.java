package at.steiner.casino.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link at.steiner.casino.domain.StockValueChange} entity.
 */
public class StockValueChangeDTO implements Serializable {

    private Long id;

    private Instant time;

    private Integer value;


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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
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

        StockValueChangeDTO stockValueChangeDTO = (StockValueChangeDTO) o;
        if (stockValueChangeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockValueChangeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockValueChangeDTO{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", value=" + getValue() +
            ", stockId=" + getStockId() +
            "}";
    }
}
