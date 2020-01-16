package at.steiner.casino.repository;

import at.steiner.casino.domain.GameEnded;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GameEnded entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameEndedRepository extends JpaRepository<GameEnded, Long> {

}
