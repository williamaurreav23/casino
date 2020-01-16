package at.steiner.casino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PlayerMoneyTransactionMapperTest {

    private PlayerMoneyTransactionMapper playerMoneyTransactionMapper;

    @BeforeEach
    public void setUp() {
        playerMoneyTransactionMapper = new PlayerMoneyTransactionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(playerMoneyTransactionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(playerMoneyTransactionMapper.fromId(null)).isNull();
    }
}
