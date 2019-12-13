package at.sebastian.service.impl

import at.sebastian.service.SpielerAktieService
import at.sebastian.domain.SpielerAktie
import at.sebastian.repository.SpielerAktieRepository
import at.sebastian.service.dto.SpielerAktieDTO
import at.sebastian.service.mapper.SpielerAktieMapper
import org.slf4j.LoggerFactory

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.util.Optional

/**
 * Service Implementation for managing [SpielerAktie].
 */
@Service
@Transactional
class SpielerAktieServiceImpl(
    private val spielerAktieRepository: SpielerAktieRepository,
    private val spielerAktieMapper: SpielerAktieMapper
) : SpielerAktieService {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Save a spielerAktie.
     *
     * @param spielerAktieDTO the entity to save.
     * @return the persisted entity.
     */
    override fun save(spielerAktieDTO: SpielerAktieDTO): SpielerAktieDTO {
        log.debug("Request to save SpielerAktie : {}", spielerAktieDTO)

        var spielerAktie = spielerAktieMapper.toEntity(spielerAktieDTO)
        spielerAktie = spielerAktieRepository.save(spielerAktie)
        return spielerAktieMapper.toDto(spielerAktie)
    }

    /**
     * Get all the spielerAkties.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    override fun findAll(): MutableList<SpielerAktieDTO> {
        log.debug("Request to get all SpielerAkties")
        return spielerAktieRepository.findAll()
            .mapTo(mutableListOf(), spielerAktieMapper::toDto)
    }

    /**
     * Get one spielerAktie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    override fun findOne(id: Long): Optional<SpielerAktieDTO> {
        log.debug("Request to get SpielerAktie : {}", id)
        return spielerAktieRepository.findById(id)
            .map(spielerAktieMapper::toDto)
    }

    /**
     * Delete the spielerAktie by id.
     *
     * @param id the id of the entity.
     */
    override fun delete(id: Long) {
        log.debug("Request to delete SpielerAktie : {}", id)

        spielerAktieRepository.deleteById(id)
    }
}
