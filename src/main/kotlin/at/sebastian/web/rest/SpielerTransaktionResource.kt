package at.sebastian.web.rest

import at.sebastian.service.SpielerTransaktionService
import at.sebastian.service.dto.SpielerTransaktionDTO
import at.sebastian.web.rest.errors.BadRequestAlertException
import io.github.jhipster.web.util.HeaderUtil
import io.github.jhipster.web.util.ResponseUtil
import java.net.URI
import java.net.URISyntaxException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private const val ENTITY_NAME = "spielerTransaktion"
/**
 * REST controller for managing [at.sebastian.domain.SpielerTransaktion].
 */
@RestController
@RequestMapping("/api")
class SpielerTransaktionResource(
    private val spielerTransaktionService: SpielerTransaktionService
) {

    private val log = LoggerFactory.getLogger(javaClass)
    @Value("\${jhipster.clientApp.name}")
    private var applicationName: String? = null

    /**
     * `POST  /spieler-transaktions` : Create a new spielerTransaktion.
     *
     * @param spielerTransaktionDTO the spielerTransaktionDTO to create.
     * @return the [ResponseEntity] with status `201 (Created)` and with body the new spielerTransaktionDTO, or with status `400 (Bad Request)` if the spielerTransaktion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/spieler-transaktions")
    fun createSpielerTransaktion(@RequestBody spielerTransaktionDTO: SpielerTransaktionDTO): ResponseEntity<SpielerTransaktionDTO> {
        log.debug("REST request to save SpielerTransaktion : {}", spielerTransaktionDTO)
        if (spielerTransaktionDTO.id != null) {
            throw BadRequestAlertException(
                "A new spielerTransaktion cannot already have an ID",
                ENTITY_NAME, "idexists"
            )
        }
        val result = spielerTransaktionService.save(spielerTransaktionDTO)
        return ResponseEntity.created(URI("/api/spieler-transaktions/" + result.id))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.id.toString()))
            .body(result)
    }

    /**
     * `PUT  /spieler-transaktions` : Updates an existing spielerTransaktion.
     *
     * @param spielerTransaktionDTO the spielerTransaktionDTO to update.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the updated spielerTransaktionDTO,
     * or with status `400 (Bad Request)` if the spielerTransaktionDTO is not valid,
     * or with status `500 (Internal Server Error)` if the spielerTransaktionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/spieler-transaktions")
    fun updateSpielerTransaktion(@RequestBody spielerTransaktionDTO: SpielerTransaktionDTO): ResponseEntity<SpielerTransaktionDTO> {
        log.debug("REST request to update SpielerTransaktion : {}", spielerTransaktionDTO)
        if (spielerTransaktionDTO.id == null) {
            throw BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull")
        }
        val result = spielerTransaktionService.save(spielerTransaktionDTO)
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName, true, ENTITY_NAME,
                     spielerTransaktionDTO.id.toString()
                )
            )
            .body(result)
    }
    /**
     * `GET  /spieler-transaktions` : get all the spielerTransaktions.
     *

     * @return the [ResponseEntity] with status `200 (OK)` and the list of spielerTransaktions in body.
     */
    @GetMapping("/spieler-transaktions")
    fun getAllSpielerTransaktions(): MutableList<SpielerTransaktionDTO> {
        log.debug("REST request to get all SpielerTransaktions")
        return spielerTransaktionService.findAll()
    }

    /**
     * `GET  /spieler-transaktions/:id` : get the "id" spielerTransaktion.
     *
     * @param id the id of the spielerTransaktionDTO to retrieve.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the spielerTransaktionDTO, or with status `404 (Not Found)`.
     */
    @GetMapping("/spieler-transaktions/{id}")
    fun getSpielerTransaktion(@PathVariable id: Long): ResponseEntity<SpielerTransaktionDTO> {
        log.debug("REST request to get SpielerTransaktion : {}", id)
        val spielerTransaktionDTO = spielerTransaktionService.findOne(id)
        return ResponseUtil.wrapOrNotFound(spielerTransaktionDTO)
    }
    /**
     *  `DELETE  /spieler-transaktions/:id` : delete the "id" spielerTransaktion.
     *
     * @param id the id of the spielerTransaktionDTO to delete.
     * @return the [ResponseEntity] with status `204 (NO_CONTENT)`.
     */
    @DeleteMapping("/spieler-transaktions/{id}")
    fun deleteSpielerTransaktion(@PathVariable id: Long): ResponseEntity<Void> {
        log.debug("REST request to delete SpielerTransaktion : {}", id)
        spielerTransaktionService.delete(id)
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build()
    }
}
