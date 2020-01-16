package at.steiner.casino.web.rest;

import at.steiner.casino.service.StockValueChangeService;
import at.steiner.casino.web.rest.errors.BadRequestAlertException;
import at.steiner.casino.service.dto.StockValueChangeDTO;

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
 * REST controller for managing {@link at.steiner.casino.domain.StockValueChange}.
 */
@RestController
@RequestMapping("/api")
public class StockValueChangeResource {

    private final Logger log = LoggerFactory.getLogger(StockValueChangeResource.class);

    private static final String ENTITY_NAME = "stockValueChange";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockValueChangeService stockValueChangeService;

    public StockValueChangeResource(StockValueChangeService stockValueChangeService) {
        this.stockValueChangeService = stockValueChangeService;
    }

    /**
     * {@code POST  /stock-value-changes} : Create a new stockValueChange.
     *
     * @param stockValueChangeDTO the stockValueChangeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockValueChangeDTO, or with status {@code 400 (Bad Request)} if the stockValueChange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-value-changes")
    public ResponseEntity<StockValueChangeDTO> createStockValueChange(@RequestBody StockValueChangeDTO stockValueChangeDTO) throws URISyntaxException {
        log.debug("REST request to save StockValueChange : {}", stockValueChangeDTO);
        if (stockValueChangeDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockValueChange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockValueChangeDTO result = stockValueChangeService.save(stockValueChangeDTO);
        return ResponseEntity.created(new URI("/api/stock-value-changes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-value-changes} : Updates an existing stockValueChange.
     *
     * @param stockValueChangeDTO the stockValueChangeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockValueChangeDTO,
     * or with status {@code 400 (Bad Request)} if the stockValueChangeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockValueChangeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-value-changes")
    public ResponseEntity<StockValueChangeDTO> updateStockValueChange(@RequestBody StockValueChangeDTO stockValueChangeDTO) throws URISyntaxException {
        log.debug("REST request to update StockValueChange : {}", stockValueChangeDTO);
        if (stockValueChangeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockValueChangeDTO result = stockValueChangeService.save(stockValueChangeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockValueChangeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stock-value-changes} : get all the stockValueChanges.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockValueChanges in body.
     */
    @GetMapping("/stock-value-changes")
    public List<StockValueChangeDTO> getAllStockValueChanges() {
        log.debug("REST request to get all StockValueChanges");
        return stockValueChangeService.findAll();
    }

    /**
     * {@code GET  /stock-value-changes/:id} : get the "id" stockValueChange.
     *
     * @param id the id of the stockValueChangeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockValueChangeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-value-changes/{id}")
    public ResponseEntity<StockValueChangeDTO> getStockValueChange(@PathVariable Long id) {
        log.debug("REST request to get StockValueChange : {}", id);
        Optional<StockValueChangeDTO> stockValueChangeDTO = stockValueChangeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockValueChangeDTO);
    }

    /**
     * {@code DELETE  /stock-value-changes/:id} : delete the "id" stockValueChange.
     *
     * @param id the id of the stockValueChangeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-value-changes/{id}")
    public ResponseEntity<Void> deleteStockValueChange(@PathVariable Long id) {
        log.debug("REST request to delete StockValueChange : {}", id);
        stockValueChangeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
