package at.sebastian.web.rest

import at.sebastian.domain.AktieWertHistory
import at.sebastian.repository.AktieWertHistoryRepository
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

private const val ENTITY_NAME = "aktieWertHistory"
/**
 * REST controller for managing [at.sebastian.domain.AktieWertHistory].
 */
@RestController
@RequestMapping("/api")
class AktieWertHistoryResource(
    private val aktieWertHistoryRepository: AktieWertHistoryRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)
    @Value("\${jhipster.clientApp.name}")
    private var applicationName: String? = null

    /**
     * `POST  /aktie-wert-histories` : Create a new aktieWertHistory.
     *
     * @param aktieWertHistory the aktieWertHistory to create.
     * @return the [ResponseEntity] with status `201 (Created)` and with body the new aktieWertHistory, or with status `400 (Bad Request)` if the aktieWertHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aktie-wert-histories")
    fun createAktieWertHistory(@RequestBody aktieWertHistory: AktieWertHistory): ResponseEntity<AktieWertHistory> {
        log.debug("REST request to save AktieWertHistory : {}", aktieWertHistory)
        if (aktieWertHistory.id != null) {
            throw BadRequestAlertException(
                "A new aktieWertHistory cannot already have an ID",
                ENTITY_NAME, "idexists"
            )
        }
        val result = aktieWertHistoryRepository.save(aktieWertHistory)
        return ResponseEntity.created(URI("/api/aktie-wert-histories/" + result.id))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.id.toString()))
            .body(result)
    }

    /**
     * `PUT  /aktie-wert-histories` : Updates an existing aktieWertHistory.
     *
     * @param aktieWertHistory the aktieWertHistory to update.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the updated aktieWertHistory,
     * or with status `400 (Bad Request)` if the aktieWertHistory is not valid,
     * or with status `500 (Internal Server Error)` if the aktieWertHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aktie-wert-histories")
    fun updateAktieWertHistory(@RequestBody aktieWertHistory: AktieWertHistory): ResponseEntity<AktieWertHistory> {
        log.debug("REST request to update AktieWertHistory : {}", aktieWertHistory)
        if (aktieWertHistory.id == null) {
            throw BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull")
        }
        val result = aktieWertHistoryRepository.save(aktieWertHistory)
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName, true, ENTITY_NAME,
                     aktieWertHistory.id.toString()
                )
            )
            .body(result)
    }
    /**
     * `GET  /aktie-wert-histories` : get all the aktieWertHistories.
     *

     * @return the [ResponseEntity] with status `200 (OK)` and the list of aktieWertHistories in body.
     */
    @GetMapping("/aktie-wert-histories")
    fun getAllAktieWertHistories(): MutableList<AktieWertHistory> {
        log.debug("REST request to get all AktieWertHistories")
        return aktieWertHistoryRepository.findAll()
    }

    /**
     * `GET  /aktie-wert-histories/:id` : get the "id" aktieWertHistory.
     *
     * @param id the id of the aktieWertHistory to retrieve.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the aktieWertHistory, or with status `404 (Not Found)`.
     */
    @GetMapping("/aktie-wert-histories/{id}")
    fun getAktieWertHistory(@PathVariable id: Long): ResponseEntity<AktieWertHistory> {
        log.debug("REST request to get AktieWertHistory : {}", id)
        val aktieWertHistory = aktieWertHistoryRepository.findById(id)
        return ResponseUtil.wrapOrNotFound(aktieWertHistory)
    }
    /**
     *  `DELETE  /aktie-wert-histories/:id` : delete the "id" aktieWertHistory.
     *
     * @param id the id of the aktieWertHistory to delete.
     * @return the [ResponseEntity] with status `204 (NO_CONTENT)`.
     */
    @DeleteMapping("/aktie-wert-histories/{id}")
    fun deleteAktieWertHistory(@PathVariable id: Long): ResponseEntity<Void> {
        log.debug("REST request to delete AktieWertHistory : {}", id)

        aktieWertHistoryRepository.deleteById(id)
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build()
    }
}
