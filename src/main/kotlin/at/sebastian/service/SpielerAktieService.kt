package at.sebastian.service

import at.sebastian.service.dto.SpielerAktieDTO

import java.util.Optional

/**
 * Service Interface for managing [at.sebastian.domain.SpielerAktie].
 */
interface SpielerAktieService {

    /**
     * Save a spielerAktie.
     *
     * @param spielerAktieDTO the entity to save.
     * @return the persisted entity.
     */
    fun save(spielerAktieDTO: SpielerAktieDTO): SpielerAktieDTO

    /**
     * Get all the spielerAkties.
     *
     * @return the list of entities.
     */
    fun findAll(): MutableList<SpielerAktieDTO>

    /**
     * Get the "id" spielerAktie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    fun findOne(id: Long): Optional<SpielerAktieDTO>

    /**
     * Delete the "id" spielerAktie.
     *
     * @param id the id of the entity.
     */
    fun delete(id: Long)
}
