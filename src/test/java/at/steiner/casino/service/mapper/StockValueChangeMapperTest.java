package at.steiner.casino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class StockValueChangeMapperTest {

    private StockValueChangeMapper stockValueChangeMapper;

    @BeforeEach
    public void setUp() {
        stockValueChangeMapper = new StockValueChangeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(stockValueChangeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stockValueChangeMapper.fromId(null)).isNull();
    }
}
