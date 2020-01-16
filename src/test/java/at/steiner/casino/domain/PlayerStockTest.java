package at.steiner.casino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import at.steiner.casino.web.rest.TestUtil;

public class PlayerStockTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerStock.class);
        PlayerStock playerStock1 = new PlayerStock();
        playerStock1.setId(1L);
        PlayerStock playerStock2 = new PlayerStock();
        playerStock2.setId(playerStock1.getId());
        assertThat(playerStock1).isEqualTo(playerStock2);
        playerStock2.setId(2L);
        assertThat(playerStock1).isNotEqualTo(playerStock2);
        playerStock1.setId(null);
        assertThat(playerStock1).isNotEqualTo(playerStock2);
    }
}
