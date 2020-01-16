package at.steiner.casino.service;

import at.steiner.casino.service.dto.PlayerMoneyTransactionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link at.steiner.casino.domain.PlayerMoneyTransaction}.
 */
public interface PlayerMoneyTransactionService {

    /**
     * Save a playerMoneyTransaction.
     *
     * @param playerMoneyTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    PlayerMoneyTransactionDTO save(PlayerMoneyTransactionDTO playerMoneyTransactionDTO);

    /**
     * Get all the playerMoneyTransactions.
     *
     * @return the list of entities.
     */
    List<PlayerMoneyTransactionDTO> findAllByPlayer(Long playerId);


    /**
     * Get the "id" playerMoneyTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlayerMoneyTransactionDTO> findOne(Long id);

    /**
     * Delete the "id" playerMoneyTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
