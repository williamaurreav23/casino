package at.steiner.casino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PlayerStockTransactionMapperTest {

    private PlayerStockTransactionMapper playerStockTransactionMapper;

    @BeforeEach
    public void setUp() {
        playerStockTransactionMapper = new PlayerStockTransactionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(playerStockTransactionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(playerStockTransactionMapper.fromId(null)).isNull();
    }
}
