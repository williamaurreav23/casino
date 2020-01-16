package at.steiner.casino.service;

import at.steiner.casino.service.dto.PlayerStockDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link at.steiner.casino.domain.PlayerStock}.
 */
public interface PlayerStockService {

    /**
     * Save a playerStock.
     *
     * @param playerStockDTO the entity to save.
     * @return the persisted entity.
     */
    PlayerStockDTO save(PlayerStockDTO playerStockDTO);

    /**
     * Get all the playerStocks.
     *
     * @return the list of entities.
     */
    List<PlayerStockDTO> findAll();


    /**
     * Get the "id" playerStock.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlayerStockDTO> findOne(Long id);

    /**
     * Get all playerStock owned by player with id.
     *
     * @param playerId the id of the owning player.
     * @return all entities.
     */
    List<PlayerStockDTO> findAllByPlayerId(Long playerId);

    /**
     * Delete the "id" playerStock.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
