package at.steiner.casino.web.rest;

import at.steiner.casino.service.PlayerStockTransactionService;
import at.steiner.casino.web.rest.errors.BadRequestAlertException;
import at.steiner.casino.service.dto.PlayerStockTransactionDTO;

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
 * REST controller for managing {@link at.steiner.casino.domain.PlayerStockTransaction}.
 */
@RestController
@RequestMapping("/api")
public class PlayerStockTransactionResource {

    private final Logger log = LoggerFactory.getLogger(PlayerStockTransactionResource.class);

    private static final String ENTITY_NAME = "playerStockTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlayerStockTransactionService playerStockTransactionService;

    public PlayerStockTransactionResource(PlayerStockTransactionService playerStockTransactionService) {
        this.playerStockTransactionService = playerStockTransactionService;
    }

    /**
     * {@code POST  /player-stock-transactions} : Create a new playerStockTransaction.
     *
     * @param playerStockTransactionDTO the playerStockTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new playerStockTransactionDTO, or with status {@code 400 (Bad Request)} if the playerStockTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/player-stock-transactions")
    public ResponseEntity<PlayerStockTransactionDTO> createPlayerStockTransaction(@RequestBody PlayerStockTransactionDTO playerStockTransactionDTO) throws URISyntaxException {
        log.debug("REST request to save PlayerStockTransaction : {}", playerStockTransactionDTO);
        if (playerStockTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new playerStockTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlayerStockTransactionDTO result = playerStockTransactionService.save(playerStockTransactionDTO);
        return ResponseEntity.created(new URI("/api/player-stock-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /player-stock-transactions} : Updates an existing playerStockTransaction.
     *
     * @param playerStockTransactionDTO the playerStockTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerStockTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the playerStockTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the playerStockTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/player-stock-transactions")
    public ResponseEntity<PlayerStockTransactionDTO> updatePlayerStockTransaction(@RequestBody PlayerStockTransactionDTO playerStockTransactionDTO) throws URISyntaxException {
        log.debug("REST request to update PlayerStockTransaction : {}", playerStockTransactionDTO);
        if (playerStockTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlayerStockTransactionDTO result = playerStockTransactionService.save(playerStockTransactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, playerStockTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /player-stock-transactions} : get all the playerStockTransactions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of playerStockTransactions in body.
     */
    @GetMapping("/player-stock-transactions")
    public List<PlayerStockTransactionDTO> getAllPlayerStockTransactions() {
        log.debug("REST request to get all PlayerStockTransactions");
        return playerStockTransactionService.findAll();
    }

    /**
     * {@code GET  /player-stock-transactions/:id} : get the "id" playerStockTransaction.
     *
     * @param id the id of the playerStockTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the playerStockTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/player-stock-transactions/{id}")
    public ResponseEntity<PlayerStockTransactionDTO> getPlayerStockTransaction(@PathVariable Long id) {
        log.debug("REST request to get PlayerStockTransaction : {}", id);
        Optional<PlayerStockTransactionDTO> playerStockTransactionDTO = playerStockTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(playerStockTransactionDTO);
    }

    /**
     * {@code DELETE  /player-stock-transactions/:id} : delete the "id" playerStockTransaction.
     *
     * @param id the id of the playerStockTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/player-stock-transactions/{id}")
    public ResponseEntity<Void> deletePlayerStockTransaction(@PathVariable Long id) {
        log.debug("REST request to delete PlayerStockTransaction : {}", id);
        playerStockTransactionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
