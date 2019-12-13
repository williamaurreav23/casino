package at.sebastian.web.rest

import at.sebastian.service.AktieWertService
import at.sebastian.web.rest.errors.BadRequestAlertException
import at.sebastian.service.dto.AktieWertDTO

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

private const val ENTITY_NAME = "aktieWert"
/**
 * REST controller for managing [at.sebastian.domain.AktieWert].
 */
@RestController
@RequestMapping("/api")
class AktieWertResource(
    private val aktieWertService: AktieWertService
) {

    private val log = LoggerFactory.getLogger(javaClass)
    @Value("\${jhipster.clientApp.name}")
    private var applicationName: String? = null

    /**
     * `POST  /aktie-werts` : Create a new aktieWert.
     *
     * @param aktieWertDTO the aktieWertDTO to create.
     * @return the [ResponseEntity] with status `201 (Created)` and with body the new aktieWertDTO, or with status `400 (Bad Request)` if the aktieWert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aktie-werts")
    fun createAktieWert(@RequestBody aktieWertDTO: AktieWertDTO): ResponseEntity<AktieWertDTO> {
        log.debug("REST request to save AktieWert : {}", aktieWertDTO)
        if (aktieWertDTO.id != null) {
            throw BadRequestAlertException(
                "A new aktieWert cannot already have an ID",
                ENTITY_NAME, "idexists"
            )
        }
        val result = aktieWertService.save(aktieWertDTO)
        return ResponseEntity.created(URI("/api/aktie-werts/" + result.id))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.id.toString()))
            .body(result)
    }

    /**
     * `PUT  /aktie-werts` : Updates an existing aktieWert.
     *
     * @param aktieWertDTO the aktieWertDTO to update.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the updated aktieWertDTO,
     * or with status `400 (Bad Request)` if the aktieWertDTO is not valid,
     * or with status `500 (Internal Server Error)` if the aktieWertDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aktie-werts")
    fun updateAktieWert(@RequestBody aktieWertDTO: AktieWertDTO): ResponseEntity<AktieWertDTO> {
        log.debug("REST request to update AktieWert : {}", aktieWertDTO)
        if (aktieWertDTO.id == null) {
            throw BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull")
        }
        val result = aktieWertService.save(aktieWertDTO)
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName, true, ENTITY_NAME,
                     aktieWertDTO.id.toString()
                )
            )
            .body(result)
    }
    /**
     * `GET  /aktie-werts` : get all the aktieWerts.
     *

     * @return the [ResponseEntity] with status `200 (OK)` and the list of aktieWerts in body.
     */
    @GetMapping("/aktie-werts")    
    fun getAllAktieWerts(): MutableList<AktieWertDTO> {
        log.debug("REST request to get all AktieWerts")
        return aktieWertService.findAll()
    }

    /**
     * `GET  /aktie-werts/:id` : get the "id" aktieWert.
     *
     * @param id the id of the aktieWertDTO to retrieve.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the aktieWertDTO, or with status `404 (Not Found)`.
     */
    @GetMapping("/aktie-werts/{id}")
    fun getAktieWert(@PathVariable id: Long): ResponseEntity<AktieWertDTO> {
        log.debug("REST request to get AktieWert : {}", id)
        val aktieWertDTO = aktieWertService.findOne(id)
        return ResponseUtil.wrapOrNotFound(aktieWertDTO)
    }
    /**
     *  `DELETE  /aktie-werts/:id` : delete the "id" aktieWert.
     *
     * @param id the id of the aktieWertDTO to delete.
     * @return the [ResponseEntity] with status `204 (NO_CONTENT)`.
     */
    @DeleteMapping("/aktie-werts/{id}")
    fun deleteAktieWert(@PathVariable id: Long): ResponseEntity<Void> {
        log.debug("REST request to delete AktieWert : {}", id)
        aktieWertService.delete(id)
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build()
    }
}
