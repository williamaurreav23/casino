package at.steiner.casino.web.rest;

import at.steiner.casino.service.CroupierService;
import at.steiner.casino.service.PlayerService;
import at.steiner.casino.service.PlayerStockService;
import at.steiner.casino.service.dto.*;
import at.steiner.casino.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link at.steiner.casino.domain.Player}.
 */
@RestController
@RequestMapping("/api")
public class CroupierResource {

    private final Logger log = LoggerFactory.getLogger(CroupierResource.class);

    private final CroupierService croupierService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;


    public CroupierResource(CroupierService croupierService) {
        this.croupierService = croupierService;
    }

    /**
     * {@code GET  /players} : get all the transaction types.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of players in body.
     */
    @GetMapping("/croupier/transactions")
    @PreAuthorize("hasAuthority('ROLE_CROUPIER')")
    public ResponseEntity<List<TransactionDTO>> getAllTransactionTypes() {
        log.debug("REST request to get all Transaction types");
        return ResponseEntity.status(200).body(croupierService.getAllTransactionTypes());
    }

    /**
     * {@code POST  /croupier/transactions/{id}} : Set the users transaction type
     */
    @PostMapping("/croupier/transactions/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_CROUPIER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> setTransactionType(@PathVariable Integer id) throws URISyntaxException {
        log.debug("REST request to set transaction type to : {}", id);
        if (croupierService.setTransactionType(id)) {
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * {@code POST  /croupier/transactions/} : Create a transaction for a player
     */
    @PostMapping("/croupier/transaction")
    @PreAuthorize("hasAnyAuthority('ROLE_CROUPIER', 'ROLE_ADMIN')")
    public ResponseEntity<PlayerMoneyTransactionDTO> createTransaction(@RequestBody PlayerMoneyTransactionDTO playerMoneyTransactionDTO) {
        log.debug("REST request to create transaction");
        return ResponseEntity.status(200).body(croupierService.createTransaction(playerMoneyTransactionDTO));
    }

}
