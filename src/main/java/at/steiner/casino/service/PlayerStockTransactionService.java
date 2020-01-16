package at.steiner.casino.service;

import at.steiner.casino.service.dto.PlayerStockTransactionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link at.steiner.casino.domain.PlayerStockTransaction}.
 */
public interface PlayerStockTransactionService {

    /**
     * Save a playerStockTransaction.
     *
     * @param playerStockTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    PlayerStockTransactionDTO save(PlayerStockTransactionDTO playerStockTransactionDTO);

    /**
     * Get all the playerStockTransactions.
     *
     * @return the list of entities.
     */
    List<PlayerStockTransactionDTO> findAll();


    /**
     * Get the "id" playerStockTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlayerStockTransactionDTO> findOne(Long id);

    /**
     * Delete the "id" playerStockTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
