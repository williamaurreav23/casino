package at.steiner.casino.service;

import at.steiner.casino.service.dto.StockValueChangeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link at.steiner.casino.domain.StockValueChange}.
 */
public interface StockValueChangeService {

    /**
     * Save a stockValueChange.
     *
     * @param stockValueChangeDTO the entity to save.
     * @return the persisted entity.
     */
    StockValueChangeDTO save(StockValueChangeDTO stockValueChangeDTO);

    /**
     * Get all the stockValueChanges.
     *
     * @return the list of entities.
     */
    List<StockValueChangeDTO> findAll();


    /**
     * Get the "id" stockValueChange.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockValueChangeDTO> findOne(Long id);

    /**
     * Delete the "id" stockValueChange.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
