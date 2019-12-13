package at.sebastian.web.rest

import at.sebastian.service.SpielerService
import at.sebastian.web.rest.errors.BadRequestAlertException
import at.sebastian.service.dto.SpielerDTO

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

private const val ENTITY_NAME = "spieler"
/**
 * REST controller for managing [at.sebastian.domain.Spieler].
 */
@RestController
@RequestMapping("/api")
class SpielerResource(
    private val spielerService: SpielerService
) {

    private val log = LoggerFactory.getLogger(javaClass)
    @Value("\${jhipster.clientApp.name}")
    private var applicationName: String? = null

    /**
     * `POST  /spielers` : Create a new spieler.
     *
     * @param spielerDTO the spielerDTO to create.
     * @return the [ResponseEntity] with status `201 (Created)` and with body the new spielerDTO, or with status `400 (Bad Request)` if the spieler has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/spielers")
    fun createSpieler(@RequestBody spielerDTO: SpielerDTO): ResponseEntity<SpielerDTO> {
        log.debug("REST request to save Spieler : {}", spielerDTO)
        if (spielerDTO.id != null) {
            throw BadRequestAlertException(
                "A new spieler cannot already have an ID",
                ENTITY_NAME, "idexists"
            )
        }
        val result = spielerService.save(spielerDTO)
        return ResponseEntity.created(URI("/api/spielers/" + result.id))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.id.toString()))
            .body(result)
    }

    /**
     * `PUT  /spielers` : Updates an existing spieler.
     *
     * @param spielerDTO the spielerDTO to update.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the updated spielerDTO,
     * or with status `400 (Bad Request)` if the spielerDTO is not valid,
     * or with status `500 (Internal Server Error)` if the spielerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/spielers")
    fun updateSpieler(@RequestBody spielerDTO: SpielerDTO): ResponseEntity<SpielerDTO> {
        log.debug("REST request to update Spieler : {}", spielerDTO)
        if (spielerDTO.id == null) {
            throw BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull")
        }
        val result = spielerService.save(spielerDTO)
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName, true, ENTITY_NAME,
                     spielerDTO.id.toString()
                )
            )
            .body(result)
    }
    /**
     * `GET  /spielers` : get all the spielers.
     *

     * @return the [ResponseEntity] with status `200 (OK)` and the list of spielers in body.
     */
    @GetMapping("/spielers")    
    fun getAllSpielers(): MutableList<SpielerDTO> {
        log.debug("REST request to get all Spielers")
        return spielerService.findAll()
    }

    /**
     * `GET  /spielers/:id` : get the "id" spieler.
     *
     * @param id the id of the spielerDTO to retrieve.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the spielerDTO, or with status `404 (Not Found)`.
     */
    @GetMapping("/spielers/{id}")
    fun getSpieler(@PathVariable id: Long): ResponseEntity<SpielerDTO> {
        log.debug("REST request to get Spieler : {}", id)
        val spielerDTO = spielerService.findOne(id)
        return ResponseUtil.wrapOrNotFound(spielerDTO)
    }
    /**
     *  `DELETE  /spielers/:id` : delete the "id" spieler.
     *
     * @param id the id of the spielerDTO to delete.
     * @return the [ResponseEntity] with status `204 (NO_CONTENT)`.
     */
    @DeleteMapping("/spielers/{id}")
    fun deleteSpieler(@PathVariable id: Long): ResponseEntity<Void> {
        log.debug("REST request to delete Spieler : {}", id)
        spielerService.delete(id)
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build()
    }
}
