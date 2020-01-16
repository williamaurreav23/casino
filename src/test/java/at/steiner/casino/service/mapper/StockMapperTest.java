package at.steiner.casino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class StockMapperTest {

    private StockMapper stockMapper;

    @BeforeEach
    public void setUp() {
        stockMapper = new StockMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(stockMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stockMapper.fromId(null)).isNull();
    }
}
