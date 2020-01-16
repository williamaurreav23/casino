package at.steiner.casino.repository;

import at.steiner.casino.domain.PlayerStockTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the PlayerStockTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerStockTransactionRepository extends JpaRepository<PlayerStockTransaction, Long> {
    List<PlayerStockTransaction> findAllByPlayerIdAndStockIdOrderByTimeAsc(Long playerId, Long stockId);
}
