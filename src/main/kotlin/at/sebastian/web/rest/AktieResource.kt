package at.sebastian.web.rest

import at.sebastian.service.AktieService
import at.sebastian.web.rest.errors.BadRequestAlertException
import at.sebastian.service.dto.AktieDTO

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

private const val ENTITY_NAME = "aktie"
/**
 * REST controller for managing [at.sebastian.domain.Aktie].
 */
@RestController
@RequestMapping("/api")
class AktieResource(
    private val aktieService: AktieService
) {

    private val log = LoggerFactory.getLogger(javaClass)
    @Value("\${jhipster.clientApp.name}")
    private var applicationName: String? = null

    /**
     * `POST  /akties` : Create a new aktie.
     *
     * @param aktieDTO the aktieDTO to create.
     * @return the [ResponseEntity] with status `201 (Created)` and with body the new aktieDTO, or with status `400 (Bad Request)` if the aktie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/akties")
    fun createAktie(@RequestBody aktieDTO: AktieDTO): ResponseEntity<AktieDTO> {
        log.debug("REST request to save Aktie : {}", aktieDTO)
        if (aktieDTO.id != null) {
            throw BadRequestAlertException(
                "A new aktie cannot already have an ID",
                ENTITY_NAME, "idexists"
            )
        }
        val result = aktieService.save(aktieDTO)
        return ResponseEntity.created(URI("/api/akties/" + result.id))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.id.toString()))
            .body(result)
    }

    /**
     * `PUT  /akties` : Updates an existing aktie.
     *
     * @param aktieDTO the aktieDTO to update.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the updated aktieDTO,
     * or with status `400 (Bad Request)` if the aktieDTO is not valid,
     * or with status `500 (Internal Server Error)` if the aktieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/akties")
    fun updateAktie(@RequestBody aktieDTO: AktieDTO): ResponseEntity<AktieDTO> {
        log.debug("REST request to update Aktie : {}", aktieDTO)
        if (aktieDTO.id == null) {
            throw BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull")
        }
        val result = aktieService.save(aktieDTO)
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName, true, ENTITY_NAME,
                     aktieDTO.id.toString()
                )
            )
            .body(result)
    }
    /**
     * `GET  /akties` : get all the akties.
     *

     * @return the [ResponseEntity] with status `200 (OK)` and the list of akties in body.
     */
    @GetMapping("/akties")    
    fun getAllAkties(): MutableList<AktieDTO> {
        log.debug("REST request to get all Akties")
        return aktieService.findAll()
    }

    /**
     * `GET  /akties/:id` : get the "id" aktie.
     *
     * @param id the id of the aktieDTO to retrieve.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the aktieDTO, or with status `404 (Not Found)`.
     */
    @GetMapping("/akties/{id}")
    fun getAktie(@PathVariable id: Long): ResponseEntity<AktieDTO> {
        log.debug("REST request to get Aktie : {}", id)
        val aktieDTO = aktieService.findOne(id)
        return ResponseUtil.wrapOrNotFound(aktieDTO)
    }
    /**
     *  `DELETE  /akties/:id` : delete the "id" aktie.
     *
     * @param id the id of the aktieDTO to delete.
     * @return the [ResponseEntity] with status `204 (NO_CONTENT)`.
     */
    @DeleteMapping("/akties/{id}")
    fun deleteAktie(@PathVariable id: Long): ResponseEntity<Void> {
        log.debug("REST request to delete Aktie : {}", id)
        aktieService.delete(id)
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build()
    }
}
