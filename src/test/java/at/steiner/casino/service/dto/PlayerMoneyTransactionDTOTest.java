package at.steiner.casino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import at.steiner.casino.web.rest.TestUtil;

public class PlayerMoneyTransactionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerMoneyTransactionDTO.class);
        PlayerMoneyTransactionDTO playerMoneyTransactionDTO1 = new PlayerMoneyTransactionDTO();
        playerMoneyTransactionDTO1.setId(1L);
        PlayerMoneyTransactionDTO playerMoneyTransactionDTO2 = new PlayerMoneyTransactionDTO();
        assertThat(playerMoneyTransactionDTO1).isNotEqualTo(playerMoneyTransactionDTO2);
        playerMoneyTransactionDTO2.setId(playerMoneyTransactionDTO1.getId());
        assertThat(playerMoneyTransactionDTO1).isEqualTo(playerMoneyTransactionDTO2);
        playerMoneyTransactionDTO2.setId(2L);
        assertThat(playerMoneyTransactionDTO1).isNotEqualTo(playerMoneyTransactionDTO2);
        playerMoneyTransactionDTO1.setId(null);
        assertThat(playerMoneyTransactionDTO1).isNotEqualTo(playerMoneyTransactionDTO2);
    }
}
