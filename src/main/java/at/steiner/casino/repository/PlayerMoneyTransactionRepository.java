package at.steiner.casino.repository;

import at.steiner.casino.domain.PlayerMoneyTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the PlayerMoneyTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerMoneyTransactionRepository extends JpaRepository<PlayerMoneyTransaction, Long> {
    List<PlayerMoneyTransaction> findAllByPlayerIdOrderByTimeAsc(Long playerId);
}
