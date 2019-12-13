package at.sebastian.service.impl

import at.sebastian.service.SpielerTransaktionService
import at.sebastian.domain.SpielerTransaktion
import at.sebastian.repository.SpielerTransaktionRepository
import at.sebastian.service.dto.SpielerTransaktionDTO
import at.sebastian.service.mapper.SpielerTransaktionMapper
import org.slf4j.LoggerFactory

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.util.Optional

/**
 * Service Implementation for managing [SpielerTransaktion].
 */
@Service
@Transactional
class SpielerTransaktionServiceImpl(
    private val spielerTransaktionRepository: SpielerTransaktionRepository,
    private val spielerTransaktionMapper: SpielerTransaktionMapper
) : SpielerTransaktionService {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Save a spielerTransaktion.
     *
     * @param spielerTransaktionDTO the entity to save.
     * @return the persisted entity.
     */
    override fun save(spielerTransaktionDTO: SpielerTransaktionDTO): SpielerTransaktionDTO {
        log.debug("Request to save SpielerTransaktion : {}", spielerTransaktionDTO)

        var spielerTransaktion = spielerTransaktionMapper.toEntity(spielerTransaktionDTO)
        spielerTransaktion = spielerTransaktionRepository.save(spielerTransaktion)
        return spielerTransaktionMapper.toDto(spielerTransaktion)
    }

    /**
     * Get all the spielerTransaktions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    override fun findAll(): MutableList<SpielerTransaktionDTO> {
        log.debug("Request to get all SpielerTransaktions")
        return spielerTransaktionRepository.findAll()
            .mapTo(mutableListOf(), spielerTransaktionMapper::toDto)
    }

    /**
     * Get one spielerTransaktion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    override fun findOne(id: Long): Optional<SpielerTransaktionDTO> {
        log.debug("Request to get SpielerTransaktion : {}", id)
        return spielerTransaktionRepository.findById(id)
            .map(spielerTransaktionMapper::toDto)
    }

    /**
     * Delete the spielerTransaktion by id.
     *
     * @param id the id of the entity.
     */
    override fun delete(id: Long) {
        log.debug("Request to delete SpielerTransaktion : {}", id)

        spielerTransaktionRepository.deleteById(id)
    }
}
