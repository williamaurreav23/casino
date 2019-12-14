package at.sebastian.web.rest

import at.sebastian.domain.SpielerAktieHistory
import at.sebastian.repository.SpielerAktieHistoryRepository
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

private const val ENTITY_NAME = "spielerAktieHistory"
/**
 * REST controller for managing [at.sebastian.domain.SpielerAktieHistory].
 */
@RestController
@RequestMapping("/api")
class SpielerAktieHistoryResource(
    private val spielerAktieHistoryRepository: SpielerAktieHistoryRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)
    @Value("\${jhipster.clientApp.name}")
    private var applicationName: String? = null

    /**
     * `POST  /spieler-aktie-histories` : Create a new spielerAktieHistory.
     *
     * @param spielerAktieHistory the spielerAktieHistory to create.
     * @return the [ResponseEntity] with status `201 (Created)` and with body the new spielerAktieHistory, or with status `400 (Bad Request)` if the spielerAktieHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/spieler-aktie-histories")
    fun createSpielerAktieHistory(@RequestBody spielerAktieHistory: SpielerAktieHistory): ResponseEntity<SpielerAktieHistory> {
        log.debug("REST request to save SpielerAktieHistory : {}", spielerAktieHistory)
        if (spielerAktieHistory.id != null) {
            throw BadRequestAlertException(
                "A new spielerAktieHistory cannot already have an ID",
                ENTITY_NAME, "idexists"
            )
        }
        val result = spielerAktieHistoryRepository.save(spielerAktieHistory)
        return ResponseEntity.created(URI("/api/spieler-aktie-histories/" + result.id))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.id.toString()))
            .body(result)
    }

    /**
     * `PUT  /spieler-aktie-histories` : Updates an existing spielerAktieHistory.
     *
     * @param spielerAktieHistory the spielerAktieHistory to update.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the updated spielerAktieHistory,
     * or with status `400 (Bad Request)` if the spielerAktieHistory is not valid,
     * or with status `500 (Internal Server Error)` if the spielerAktieHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/spieler-aktie-histories")
    fun updateSpielerAktieHistory(@RequestBody spielerAktieHistory: SpielerAktieHistory): ResponseEntity<SpielerAktieHistory> {
        log.debug("REST request to update SpielerAktieHistory : {}", spielerAktieHistory)
        if (spielerAktieHistory.id == null) {
            throw BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull")
        }
        val result = spielerAktieHistoryRepository.save(spielerAktieHistory)
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName, true, ENTITY_NAME,
                     spielerAktieHistory.id.toString()
                )
            )
            .body(result)
    }
    /**
     * `GET  /spieler-aktie-histories` : get all the spielerAktieHistories.
     *

     * @return the [ResponseEntity] with status `200 (OK)` and the list of spielerAktieHistories in body.
     */
    @GetMapping("/spieler-aktie-histories")
    fun getAllSpielerAktieHistories(): MutableList<SpielerAktieHistory> {
        log.debug("REST request to get all SpielerAktieHistories")
        return spielerAktieHistoryRepository.findAll()
    }

    /**
     * `GET  /spieler-aktie-histories/:id` : get the "id" spielerAktieHistory.
     *
     * @param id the id of the spielerAktieHistory to retrieve.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the spielerAktieHistory, or with status `404 (Not Found)`.
     */
    @GetMapping("/spieler-aktie-histories/{id}")
    fun getSpielerAktieHistory(@PathVariable id: Long): ResponseEntity<SpielerAktieHistory> {
        log.debug("REST request to get SpielerAktieHistory : {}", id)
        val spielerAktieHistory = spielerAktieHistoryRepository.findById(id)
        return ResponseUtil.wrapOrNotFound(spielerAktieHistory)
    }
    /**
     *  `DELETE  /spieler-aktie-histories/:id` : delete the "id" spielerAktieHistory.
     *
     * @param id the id of the spielerAktieHistory to delete.
     * @return the [ResponseEntity] with status `204 (NO_CONTENT)`.
     */
    @DeleteMapping("/spieler-aktie-histories/{id}")
    fun deleteSpielerAktieHistory(@PathVariable id: Long): ResponseEntity<Void> {
        log.debug("REST request to delete SpielerAktieHistory : {}", id)

        spielerAktieHistoryRepository.deleteById(id)
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build()
    }
}
