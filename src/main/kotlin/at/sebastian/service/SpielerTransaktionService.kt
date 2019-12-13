package at.sebastian.service

import at.sebastian.service.dto.SpielerTransaktionDTO

import java.util.Optional

/**
 * Service Interface for managing [at.sebastian.domain.SpielerTransaktion].
 */
interface SpielerTransaktionService {

    /**
     * Save a spielerTransaktion.
     *
     * @param spielerTransaktionDTO the entity to save.
     * @return the persisted entity.
     */
    fun save(spielerTransaktionDTO: SpielerTransaktionDTO): SpielerTransaktionDTO

    /**
     * Get all the spielerTransaktions.
     *
     * @return the list of entities.
     */
    fun findAll(): MutableList<SpielerTransaktionDTO>

    /**
     * Get the "id" spielerTransaktion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    fun findOne(id: Long): Optional<SpielerTransaktionDTO>

    /**
     * Delete the "id" spielerTransaktion.
     *
     * @param id the id of the entity.
     */
    fun delete(id: Long)
}
