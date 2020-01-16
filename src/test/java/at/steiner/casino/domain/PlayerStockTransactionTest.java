package at.steiner.casino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import at.steiner.casino.web.rest.TestUtil;

public class PlayerStockTransactionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerStockTransaction.class);
        PlayerStockTransaction playerStockTransaction1 = new PlayerStockTransaction();
        playerStockTransaction1.setId(1L);
        PlayerStockTransaction playerStockTransaction2 = new PlayerStockTransaction();
        playerStockTransaction2.setId(playerStockTransaction1.getId());
        assertThat(playerStockTransaction1).isEqualTo(playerStockTransaction2);
        playerStockTransaction2.setId(2L);
        assertThat(playerStockTransaction1).isNotEqualTo(playerStockTransaction2);
        playerStockTransaction1.setId(null);
        assertThat(playerStockTransaction1).isNotEqualTo(playerStockTransaction2);
    }
}
