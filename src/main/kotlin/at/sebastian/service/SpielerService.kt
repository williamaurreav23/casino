package at.sebastian.service

import at.sebastian.service.dto.SpielerDTO
import java.util.Optional

/**
 * Service Interface for managing [at.sebastian.domain.Spieler].
 */
interface SpielerService {

    /**
     * Save a spieler.
     *
     * @param spielerDTO the entity to save.
     * @return the persisted entity.
     */
    fun save(spielerDTO: SpielerDTO): SpielerDTO

    /**
     * Get all the spielers.
     *
     * @return the list of entities.
     */
    fun findAll(): MutableList<SpielerDTO>

    /**
     * Get the "id" spieler.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    fun findOne(id: Long): Optional<SpielerDTO>

    /**
     * Delete the "id" spieler.
     *
     * @param id the id of the entity.
     */
    fun delete(id: Long)
}
