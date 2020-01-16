package at.steiner.casino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import at.steiner.casino.web.rest.TestUtil;

public class StockValueChangeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockValueChangeDTO.class);
        StockValueChangeDTO stockValueChangeDTO1 = new StockValueChangeDTO();
        stockValueChangeDTO1.setId(1L);
        StockValueChangeDTO stockValueChangeDTO2 = new StockValueChangeDTO();
        assertThat(stockValueChangeDTO1).isNotEqualTo(stockValueChangeDTO2);
        stockValueChangeDTO2.setId(stockValueChangeDTO1.getId());
        assertThat(stockValueChangeDTO1).isEqualTo(stockValueChangeDTO2);
        stockValueChangeDTO2.setId(2L);
        assertThat(stockValueChangeDTO1).isNotEqualTo(stockValueChangeDTO2);
        stockValueChangeDTO1.setId(null);
        assertThat(stockValueChangeDTO1).isNotEqualTo(stockValueChangeDTO2);
    }
}
