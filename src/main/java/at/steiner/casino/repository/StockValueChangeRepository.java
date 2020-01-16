package at.steiner.casino.repository;

import at.steiner.casino.domain.StockValueChange;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StockValueChange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockValueChangeRepository extends JpaRepository<StockValueChange, Long> {

}
