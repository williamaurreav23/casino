package at.steiner.casino.web.rest;

import at.steiner.casino.service.PlayerStockService;
import at.steiner.casino.web.rest.errors.BadRequestAlertException;
import at.steiner.casino.service.dto.PlayerStockDTO;

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
 * REST controller for managing {@link at.steiner.casino.domain.PlayerStock}.
 */
@RestController
@RequestMapping("/api")
public class PlayerStockResource {

    private final Logger log = LoggerFactory.getLogger(PlayerStockResource.class);

    private static final String ENTITY_NAME = "playerStock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlayerStockService playerStockService;

    public PlayerStockResource(PlayerStockService playerStockService) {
        this.playerStockService = playerStockService;
    }

    /**
     * {@code POST  /player-stocks} : Create a new playerStock.
     *
     * @param playerStockDTO the playerStockDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new playerStockDTO, or with status {@code 400 (Bad Request)} if the playerStock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/player-stocks")
    public ResponseEntity<PlayerStockDTO> createPlayerStock(@RequestBody PlayerStockDTO playerStockDTO) throws URISyntaxException {
        log.debug("REST request to save PlayerStock : {}", playerStockDTO);
        if (playerStockDTO.getId() != null) {
            throw new BadRequestAlertException("A new playerStock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlayerStockDTO result = playerStockService.save(playerStockDTO);
        return ResponseEntity.created(new URI("/api/player-stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /player-stocks} : Updates an existing playerStock.
     *
     * @param playerStockDTO the playerStockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerStockDTO,
     * or with status {@code 400 (Bad Request)} if the playerStockDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the playerStockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/player-stocks")
    public ResponseEntity<PlayerStockDTO> updatePlayerStock(@RequestBody PlayerStockDTO playerStockDTO) throws URISyntaxException {
        log.debug("REST request to update PlayerStock : {}", playerStockDTO);
        if (playerStockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlayerStockDTO result = playerStockService.save(playerStockDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, playerStockDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /player-stocks} : get all the playerStocks.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of playerStocks in body.
     */
    @GetMapping("/player-stocks")
    public List<PlayerStockDTO> getAllPlayerStocks() {
        log.debug("REST request to get all PlayerStocks");
        return playerStockService.findAll();
    }

    /**
     * {@code GET  /player-stocks/:id} : get the "id" playerStock.
     *
     * @param id the id of the playerStockDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the playerStockDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/player-stocks/{id}")
    public ResponseEntity<PlayerStockDTO> getPlayerStock(@PathVariable Long id) {
        log.debug("REST request to get PlayerStock : {}", id);
        Optional<PlayerStockDTO> playerStockDTO = playerStockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(playerStockDTO);
    }

    /**
     * {@code DELETE  /player-stocks/:id} : delete the "id" playerStock.
     *
     * @param id the id of the playerStockDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/player-stocks/{id}")
    public ResponseEntity<Void> deletePlayerStock(@PathVariable Long id) {
        log.debug("REST request to delete PlayerStock : {}", id);
        playerStockService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
