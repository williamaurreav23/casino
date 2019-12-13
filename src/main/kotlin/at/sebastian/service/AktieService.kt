package at.sebastian.service

import at.sebastian.service.dto.AktieDTO

import java.util.Optional

/**
 * Service Interface for managing [at.sebastian.domain.Aktie].
 */
interface AktieService {

    /**
     * Save a aktie.
     *
     * @param aktieDTO the entity to save.
     * @return the persisted entity.
     */
    fun save(aktieDTO: AktieDTO): AktieDTO

    /**
     * Get all the akties.
     *
     * @return the list of entities.
     */
    fun findAll(): MutableList<AktieDTO>

    /**
     * Get the "id" aktie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    fun findOne(id: Long): Optional<AktieDTO>

    /**
     * Delete the "id" aktie.
     *
     * @param id the id of the entity.
     */
    fun delete(id: Long)
}
