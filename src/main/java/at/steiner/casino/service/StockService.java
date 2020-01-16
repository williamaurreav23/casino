package at.steiner.casino.service;

import at.steiner.casino.service.dto.StockDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link at.steiner.casino.domain.Stock}.
 */
public interface StockService {

    /**
     * Save a stock.
     *
     * @param stockDTO the entity to save.
     * @return the persisted entity.
     */
    StockDTO save(StockDTO stockDTO);

    /**
     * Get all the stocks.
     *
     * @return the list of entities.
     */
    List<StockDTO> findAll();


    /**
     * Get the "id" stock.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockDTO> findOne(Long id);

    /**
     * Delete the "id" stock.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
