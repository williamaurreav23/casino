package at.steiner.casino.repository;

import at.steiner.casino.domain.PlayerStockTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlayerStockTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerStockTransactionRepository extends JpaRepository<PlayerStockTransaction, Long> {

}
