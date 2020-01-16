package at.steiner.casino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import at.steiner.casino.web.rest.TestUtil;

public class GameEndedTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameEnded.class);
        GameEnded gameEnded1 = new GameEnded();
        gameEnded1.setId(1L);
        GameEnded gameEnded2 = new GameEnded();
        gameEnded2.setId(gameEnded1.getId());
        assertThat(gameEnded1).isEqualTo(gameEnded2);
        gameEnded2.setId(2L);
        assertThat(gameEnded1).isNotEqualTo(gameEnded2);
        gameEnded1.setId(null);
        assertThat(gameEnded1).isNotEqualTo(gameEnded2);
    }
}
