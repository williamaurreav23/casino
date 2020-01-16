package at.steiner.casino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PlayerStockMapperTest {

    private PlayerStockMapper playerStockMapper;

    @BeforeEach
    public void setUp() {
        playerStockMapper = new PlayerStockMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(playerStockMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(playerStockMapper.fromId(null)).isNull();
    }
}
