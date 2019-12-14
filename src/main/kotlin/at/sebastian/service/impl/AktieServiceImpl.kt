package at.sebastian.service.impl

import at.sebastian.domain.Aktie
import at.sebastian.repository.AktieRepository
import at.sebastian.service.AktieService
import at.sebastian.service.dto.AktieDTO
import at.sebastian.service.mapper.AktieMapper
import java.util.Optional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service Implementation for managing [Aktie].
 */
@Service
@Transactional
class AktieServiceImpl(
    private val aktieRepository: AktieRepository,
    private val aktieMapper: AktieMapper
) : AktieService {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Save a aktie.
     *
     * @param aktieDTO the entity to save.
     * @return the persisted entity.
     */
    override fun save(aktieDTO: AktieDTO): AktieDTO {
        log.debug("Request to save Aktie : {}", aktieDTO)

        var aktie = aktieMapper.toEntity(aktieDTO)
        aktie = aktieRepository.save(aktie)
        return aktieMapper.toDto(aktie)
    }

    /**
     * Get all the akties.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    override fun findAll(): MutableList<AktieDTO> {
        log.debug("Request to get all Akties")
        return aktieRepository.findAll()
            .mapTo(mutableListOf(), aktieMapper::toDto)
    }

    /**
     * Get one aktie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    override fun findOne(id: Long): Optional<AktieDTO> {
        log.debug("Request to get Aktie : {}", id)
        return aktieRepository.findById(id)
            .map(aktieMapper::toDto)
    }

    /**
     * Delete the aktie by id.
     *
     * @param id the id of the entity.
     */
    override fun delete(id: Long) {
        log.debug("Request to delete Aktie : {}", id)

        aktieRepository.deleteById(id)
    }
}
