package at.steiner.casino.web.rest;

import at.steiner.casino.service.CroupierService;
import at.steiner.casino.service.RegistrationService;
import at.steiner.casino.service.dto.PlayerMoneyTransactionDTO;
import at.steiner.casino.service.dto.RegistrationDTO;
import at.steiner.casino.service.dto.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link at.steiner.casino.domain.Player}.
 */
@RestController
@RequestMapping("/api")
public class RegistrationResource {

    private final Logger log = LoggerFactory.getLogger(RegistrationResource.class);

    private final RegistrationService registrationService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;


    public RegistrationResource(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * {@code POST  /registrar} : Register a new player
     */
    @PostMapping("/registrar")
    @PreAuthorize("hasAnyAuthority('ROLE_REGISTRAR', 'ROLE_ADMIN')")
    public ResponseEntity<Void> registerPlayer(@RequestBody RegistrationDTO registrationDTO) {
        log.debug("REST request to register new player");
        registrationService.registerPlayer(registrationDTO);
        return ResponseEntity.status(200).build();
    }

}
