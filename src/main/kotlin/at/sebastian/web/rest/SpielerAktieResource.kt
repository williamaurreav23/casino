package at.sebastian.web.rest

import at.sebastian.service.SpielerAktieService
import at.sebastian.web.rest.errors.BadRequestAlertException
import at.sebastian.service.dto.SpielerAktieDTO

import io.github.jhipster.web.util.HeaderUtil
import io.github.jhipster.web.util.ResponseUtil
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import java.net.URI
import java.net.URISyntaxException

private const val ENTITY_NAME = "spielerAktie"
/**
 * REST controller for managing [at.sebastian.domain.SpielerAktie].
 */
@RestController
@RequestMapping("/api")
class SpielerAktieResource(
    private val spielerAktieService: SpielerAktieService
) {

    private val log = LoggerFactory.getLogger(javaClass)
    @Value("\${jhipster.clientApp.name}")
    private var applicationName: String? = null

    /**
     * `POST  /spieler-akties` : Create a new spielerAktie.
     *
     * @param spielerAktieDTO the spielerAktieDTO to create.
     * @return the [ResponseEntity] with status `201 (Created)` and with body the new spielerAktieDTO, or with status `400 (Bad Request)` if the spielerAktie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/spieler-akties")
    fun createSpielerAktie(@RequestBody spielerAktieDTO: SpielerAktieDTO): ResponseEntity<SpielerAktieDTO> {
        log.debug("REST request to save SpielerAktie : {}", spielerAktieDTO)
        if (spielerAktieDTO.id != null) {
            throw BadRequestAlertException(
                "A new spielerAktie cannot already have an ID",
                ENTITY_NAME, "idexists"
            )
        }
        val result = spielerAktieService.save(spielerAktieDTO)
        return ResponseEntity.created(URI("/api/spieler-akties/" + result.id))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.id.toString()))
            .body(result)
    }

    /**
     * `PUT  /spieler-akties` : Updates an existing spielerAktie.
     *
     * @param spielerAktieDTO the spielerAktieDTO to update.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the updated spielerAktieDTO,
     * or with status `400 (Bad Request)` if the spielerAktieDTO is not valid,
     * or with status `500 (Internal Server Error)` if the spielerAktieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/spieler-akties")
    fun updateSpielerAktie(@RequestBody spielerAktieDTO: SpielerAktieDTO): ResponseEntity<SpielerAktieDTO> {
        log.debug("REST request to update SpielerAktie : {}", spielerAktieDTO)
        if (spielerAktieDTO.id == null) {
            throw BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull")
        }
        val result = spielerAktieService.save(spielerAktieDTO)
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName, true, ENTITY_NAME,
                     spielerAktieDTO.id.toString()
                )
            )
            .body(result)
    }
    /**
     * `GET  /spieler-akties` : get all the spielerAkties.
     *

     * @return the [ResponseEntity] with status `200 (OK)` and the list of spielerAkties in body.
     */
    @GetMapping("/spieler-akties")    
    fun getAllSpielerAkties(): MutableList<SpielerAktieDTO> {
        log.debug("REST request to get all SpielerAkties")
        return spielerAktieService.findAll()
    }

    /**
     * `GET  /spieler-akties/:id` : get the "id" spielerAktie.
     *
     * @param id the id of the spielerAktieDTO to retrieve.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the spielerAktieDTO, or with status `404 (Not Found)`.
     */
    @GetMapping("/spieler-akties/{id}")
    fun getSpielerAktie(@PathVariable id: Long): ResponseEntity<SpielerAktieDTO> {
        log.debug("REST request to get SpielerAktie : {}", id)
        val spielerAktieDTO = spielerAktieService.findOne(id)
        return ResponseUtil.wrapOrNotFound(spielerAktieDTO)
    }
    /**
     *  `DELETE  /spieler-akties/:id` : delete the "id" spielerAktie.
     *
     * @param id the id of the spielerAktieDTO to delete.
     * @return the [ResponseEntity] with status `204 (NO_CONTENT)`.
     */
    @DeleteMapping("/spieler-akties/{id}")
    fun deleteSpielerAktie(@PathVariable id: Long): ResponseEntity<Void> {
        log.debug("REST request to delete SpielerAktie : {}", id)
        spielerAktieService.delete(id)
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build()
    }
}
