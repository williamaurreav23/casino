package at.steiner.casino.repository;

import at.steiner.casino.domain.PlayerStock;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlayerStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerStockRepository extends JpaRepository<PlayerStock, Long> {

}
