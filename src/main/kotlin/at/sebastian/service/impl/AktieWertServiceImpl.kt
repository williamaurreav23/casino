package at.sebastian.service.impl

import at.sebastian.service.AktieWertService
import at.sebastian.domain.AktieWert
import at.sebastian.repository.AktieWertRepository
import at.sebastian.service.dto.AktieWertDTO
import at.sebastian.service.mapper.AktieWertMapper
import org.slf4j.LoggerFactory

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.util.Optional

/**
 * Service Implementation for managing [AktieWert].
 */
@Service
@Transactional
class AktieWertServiceImpl(
    private val aktieWertRepository: AktieWertRepository,
    private val aktieWertMapper: AktieWertMapper
) : AktieWertService {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Save a aktieWert.
     *
     * @param aktieWertDTO the entity to save.
     * @return the persisted entity.
     */
    override fun save(aktieWertDTO: AktieWertDTO): AktieWertDTO {
        log.debug("Request to save AktieWert : {}", aktieWertDTO)

        var aktieWert = aktieWertMapper.toEntity(aktieWertDTO)
        aktieWert = aktieWertRepository.save(aktieWert)
        return aktieWertMapper.toDto(aktieWert)
    }

    /**
     * Get all the aktieWerts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    override fun findAll(): MutableList<AktieWertDTO> {
        log.debug("Request to get all AktieWerts")
        return aktieWertRepository.findAll()
            .mapTo(mutableListOf(), aktieWertMapper::toDto)
    }

    /**
     * Get one aktieWert by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    override fun findOne(id: Long): Optional<AktieWertDTO> {
        log.debug("Request to get AktieWert : {}", id)
        return aktieWertRepository.findById(id)
            .map(aktieWertMapper::toDto)
    }

    /**
     * Delete the aktieWert by id.
     *
     * @param id the id of the entity.
     */
    override fun delete(id: Long) {
        log.debug("Request to delete AktieWert : {}", id)

        aktieWertRepository.deleteById(id)
    }
}
