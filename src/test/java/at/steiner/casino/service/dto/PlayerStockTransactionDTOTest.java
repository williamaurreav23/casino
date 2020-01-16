package at.steiner.casino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import at.steiner.casino.web.rest.TestUtil;

public class PlayerStockTransactionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerStockTransactionDTO.class);
        PlayerStockTransactionDTO playerStockTransactionDTO1 = new PlayerStockTransactionDTO();
        playerStockTransactionDTO1.setId(1L);
        PlayerStockTransactionDTO playerStockTransactionDTO2 = new PlayerStockTransactionDTO();
        assertThat(playerStockTransactionDTO1).isNotEqualTo(playerStockTransactionDTO2);
        playerStockTransactionDTO2.setId(playerStockTransactionDTO1.getId());
        assertThat(playerStockTransactionDTO1).isEqualTo(playerStockTransactionDTO2);
        playerStockTransactionDTO2.setId(2L);
        assertThat(playerStockTransactionDTO1).isNotEqualTo(playerStockTransactionDTO2);
        playerStockTransactionDTO1.setId(null);
        assertThat(playerStockTransactionDTO1).isNotEqualTo(playerStockTransactionDTO2);
    }
}
