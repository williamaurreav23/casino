package at.steiner.casino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import at.steiner.casino.web.rest.TestUtil;

public class PlayerStockDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerStockDTO.class);
        PlayerStockDTO playerStockDTO1 = new PlayerStockDTO();
        playerStockDTO1.setId(1L);
        PlayerStockDTO playerStockDTO2 = new PlayerStockDTO();
        assertThat(playerStockDTO1).isNotEqualTo(playerStockDTO2);
        playerStockDTO2.setId(playerStockDTO1.getId());
        assertThat(playerStockDTO1).isEqualTo(playerStockDTO2);
        playerStockDTO2.setId(2L);
        assertThat(playerStockDTO1).isNotEqualTo(playerStockDTO2);
        playerStockDTO1.setId(null);
        assertThat(playerStockDTO1).isNotEqualTo(playerStockDTO2);
    }
}
