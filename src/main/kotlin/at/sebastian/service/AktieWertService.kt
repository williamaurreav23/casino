package at.sebastian.service

import at.sebastian.service.dto.AktieWertDTO

import java.util.Optional

/**
 * Service Interface for managing [at.sebastian.domain.AktieWert].
 */
interface AktieWertService {

    /**
     * Save a aktieWert.
     *
     * @param aktieWertDTO the entity to save.
     * @return the persisted entity.
     */
    fun save(aktieWertDTO: AktieWertDTO): AktieWertDTO

    /**
     * Get all the aktieWerts.
     *
     * @return the list of entities.
     */
    fun findAll(): MutableList<AktieWertDTO>

    /**
     * Get the "id" aktieWert.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    fun findOne(id: Long): Optional<AktieWertDTO>

    /**
     * Delete the "id" aktieWert.
     *
     * @param id the id of the entity.
     */
    fun delete(id: Long)
}
