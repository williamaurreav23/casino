package at.steiner.casino.web.rest;

import at.steiner.casino.domain.GameEnded;
import at.steiner.casino.repository.GameEndedRepository;
import at.steiner.casino.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link at.steiner.casino.domain.GameEnded}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GameEndedResource {

    private final Logger log = LoggerFactory.getLogger(GameEndedResource.class);

    private static final String ENTITY_NAME = "gameEnded";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameEndedRepository gameEndedRepository;

    public GameEndedResource(GameEndedRepository gameEndedRepository) {
        this.gameEndedRepository = gameEndedRepository;
    }

    /**
     * {@code POST  /game-endeds} : Create a new gameEnded.
     *
     * @param gameEnded the gameEnded to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameEnded, or with status {@code 400 (Bad Request)} if the gameEnded has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-endeds")
    public ResponseEntity<GameEnded> createGameEnded(@RequestBody GameEnded gameEnded) throws URISyntaxException {
        log.debug("REST request to save GameEnded : {}", gameEnded);
        if (gameEnded.getId() != null) {
            throw new BadRequestAlertException("A new gameEnded cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameEnded result = gameEndedRepository.save(gameEnded);
        return ResponseEntity.created(new URI("/api/game-endeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-endeds} : Updates an existing gameEnded.
     *
     * @param gameEnded the gameEnded to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameEnded,
     * or with status {@code 400 (Bad Request)} if the gameEnded is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameEnded couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-endeds")
    public ResponseEntity<GameEnded> updateGameEnded(@RequestBody GameEnded gameEnded) throws URISyntaxException {
        log.debug("REST request to update GameEnded : {}", gameEnded);
        if (gameEnded.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GameEnded result = gameEndedRepository.save(gameEnded);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameEnded.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-endeds} : get all the gameEndeds.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameEndeds in body.
     */
    @GetMapping("/game-endeds")
    public List<GameEnded> getAllGameEndeds() {
        log.debug("REST request to get all GameEndeds");
        return gameEndedRepository.findAll();
    }

    /**
     * {@code GET  /game-endeds/:id} : get the "id" gameEnded.
     *
     * @param id the id of the gameEnded to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameEnded, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-endeds/{id}")
    public ResponseEntity<GameEnded> getGameEnded(@PathVariable Long id) {
        log.debug("REST request to get GameEnded : {}", id);
        Optional<GameEnded> gameEnded = gameEndedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gameEnded);
    }

    /**
     * {@code DELETE  /game-endeds/:id} : delete the "id" gameEnded.
     *
     * @param id the id of the gameEnded to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-endeds/{id}")
    public ResponseEntity<Void> deleteGameEnded(@PathVariable Long id) {
        log.debug("REST request to delete GameEnded : {}", id);
        gameEndedRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
