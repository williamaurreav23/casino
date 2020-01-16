package at.steiner.casino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import at.steiner.casino.web.rest.TestUtil;

public class StockValueChangeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockValueChange.class);
        StockValueChange stockValueChange1 = new StockValueChange();
        stockValueChange1.setId(1L);
        StockValueChange stockValueChange2 = new StockValueChange();
        stockValueChange2.setId(stockValueChange1.getId());
        assertThat(stockValueChange1).isEqualTo(stockValueChange2);
        stockValueChange2.setId(2L);
        assertThat(stockValueChange1).isNotEqualTo(stockValueChange2);
        stockValueChange1.setId(null);
        assertThat(stockValueChange1).isNotEqualTo(stockValueChange2);
    }
}
