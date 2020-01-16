package at.steiner.casino.repository;

import at.steiner.casino.domain.PlayerStock;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the PlayerStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerStockRepository extends JpaRepository<PlayerStock, Long> {
    List<PlayerStock> findAllByPlayerId(Long playerId);

    Optional<PlayerStock> getByPlayerIdAndStockId(Long playerId, Long stockId);
}
