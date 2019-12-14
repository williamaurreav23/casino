package at.sebastian.service.impl

import at.sebastian.domain.Spieler
import at.sebastian.repository.SpielerRepository
import at.sebastian.service.SpielerService
import at.sebastian.service.dto.SpielerDTO
import at.sebastian.service.mapper.SpielerMapper
import java.util.Optional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service Implementation for managing [Spieler].
 */
@Service
@Transactional
class SpielerServiceImpl(
    private val spielerRepository: SpielerRepository,
    private val spielerMapper: SpielerMapper
) : SpielerService {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Save a spieler.
     *
     * @param spielerDTO the entity to save.
     * @return the persisted entity.
     */
    override fun save(spielerDTO: SpielerDTO): SpielerDTO {
        log.debug("Request to save Spieler : {}", spielerDTO)

        var spieler = spielerMapper.toEntity(spielerDTO)
        spieler = spielerRepository.save(spieler)
        return spielerMapper.toDto(spieler)
    }

    /**
     * Get all the spielers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    override fun findAll(): MutableList<SpielerDTO> {
        log.debug("Request to get all Spielers")
        return spielerRepository.findAll()
            .mapTo(mutableListOf(), spielerMapper::toDto)
    }

    /**
     * Get one spieler by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    override fun findOne(id: Long): Optional<SpielerDTO> {
        log.debug("Request to get Spieler : {}", id)
        return spielerRepository.findById(id)
            .map(spielerMapper::toDto)
    }

    /**
     * Delete the spieler by id.
     *
     * @param id the id of the entity.
     */
    override fun delete(id: Long) {
        log.debug("Request to delete Spieler : {}", id)

        spielerRepository.deleteById(id)
    }
}
