package at.steiner.casino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import at.steiner.casino.web.rest.TestUtil;

public class PlayerMoneyTransactionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerMoneyTransaction.class);
        PlayerMoneyTransaction playerMoneyTransaction1 = new PlayerMoneyTransaction();
        playerMoneyTransaction1.setId(1L);
        PlayerMoneyTransaction playerMoneyTransaction2 = new PlayerMoneyTransaction();
        playerMoneyTransaction2.setId(playerMoneyTransaction1.getId());
        assertThat(playerMoneyTransaction1).isEqualTo(playerMoneyTransaction2);
        playerMoneyTransaction2.setId(2L);
        assertThat(playerMoneyTransaction1).isNotEqualTo(playerMoneyTransaction2);
        playerMoneyTransaction1.setId(null);
        assertThat(playerMoneyTransaction1).isNotEqualTo(playerMoneyTransaction2);
    }
}
