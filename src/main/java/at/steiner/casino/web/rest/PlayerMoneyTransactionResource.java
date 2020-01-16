package at.steiner.casino.web.rest;

import at.steiner.casino.service.PlayerMoneyTransactionService;
import at.steiner.casino.web.rest.errors.BadRequestAlertException;
import at.steiner.casino.service.dto.PlayerMoneyTransactionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link at.steiner.casino.domain.PlayerMoneyTransaction}.
 */
@RestController
@RequestMapping("/api")
public class PlayerMoneyTransactionResource {

    private final Logger log = LoggerFactory.getLogger(PlayerMoneyTransactionResource.class);

    private static final String ENTITY_NAME = "playerMoneyTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlayerMoneyTransactionService playerMoneyTransactionService;

    public PlayerMoneyTransactionResource(PlayerMoneyTransactionService playerMoneyTransactionService) {
        this.playerMoneyTransactionService = playerMoneyTransactionService;
    }

    /**
     * {@code POST  /player-money-transactions} : Create a new playerMoneyTransaction.
     *
     * @param playerMoneyTransactionDTO the playerMoneyTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new playerMoneyTransactionDTO, or with status {@code 400 (Bad Request)} if the playerMoneyTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/player-money-transactions")
    public ResponseEntity<PlayerMoneyTransactionDTO> createPlayerMoneyTransaction(@RequestBody PlayerMoneyTransactionDTO playerMoneyTransactionDTO) throws URISyntaxException {
        log.debug("REST request to save PlayerMoneyTransaction : {}", playerMoneyTransactionDTO);
        if (playerMoneyTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new playerMoneyTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlayerMoneyTransactionDTO result = playerMoneyTransactionService.save(playerMoneyTransactionDTO);
        return ResponseEntity.created(new URI("/api/player-money-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /player-money-transactions} : Updates an existing playerMoneyTransaction.
     *
     * @param playerMoneyTransactionDTO the playerMoneyTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerMoneyTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the playerMoneyTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the playerMoneyTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/player-money-transactions")
    public ResponseEntity<PlayerMoneyTransactionDTO> updatePlayerMoneyTransaction(@RequestBody PlayerMoneyTransactionDTO playerMoneyTransactionDTO) throws URISyntaxException {
        log.debug("REST request to update PlayerMoneyTransaction : {}", playerMoneyTransactionDTO);
        if (playerMoneyTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlayerMoneyTransactionDTO result = playerMoneyTransactionService.save(playerMoneyTransactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, playerMoneyTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /player-money-transactions} : get all the playerMoneyTransactions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of playerMoneyTransactions in body.
     */
    @GetMapping("/player-money-transactions")
    public List<PlayerMoneyTransactionDTO> getAllPlayerMoneyTransactions() {
        log.debug("REST request to get all PlayerMoneyTransactions");
        return playerMoneyTransactionService.findAll();
    }

    /**
     * {@code GET  /player-money-transactions/:id} : get the "id" playerMoneyTransaction.
     *
     * @param id the id of the playerMoneyTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the playerMoneyTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/player-money-transactions/{id}")
    public ResponseEntity<PlayerMoneyTransactionDTO> getPlayerMoneyTransaction(@PathVariable Long id) {
        log.debug("REST request to get PlayerMoneyTransaction : {}", id);
        Optional<PlayerMoneyTransactionDTO> playerMoneyTransactionDTO = playerMoneyTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(playerMoneyTransactionDTO);
    }

    /**
     * {@code DELETE  /player-money-transactions/:id} : delete the "id" playerMoneyTransaction.
     *
     * @param id the id of the playerMoneyTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/player-money-transactions/{id}")
    public ResponseEntity<Void> deletePlayerMoneyTransaction(@PathVariable Long id) {
        log.debug("REST request to delete PlayerMoneyTransaction : {}", id);
        playerMoneyTransactionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
