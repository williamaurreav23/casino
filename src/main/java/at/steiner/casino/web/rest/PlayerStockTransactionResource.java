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
    @GetMapping("/player-stock-transactions/{playerId}/{stockId}")
    public List<PlayerStockTransactionDTO> getAllPlayerStockTransactions(@PathVariable Long playerId, @PathVariable Long stockId) {
        log.debug("REST request to get all PlayerStockTransactions");
        return playerStockTransactionService.findAllByPlayerStock(playerId, stockId);
    }
}
