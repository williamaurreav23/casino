package at.steiner.casino.service;

import at.steiner.casino.domain.enumeration.Transaction;
import at.steiner.casino.service.dto.PlayerMoneyTransactionDTO;
import at.steiner.casino.service.dto.RegistrationDTO;
import at.steiner.casino.service.dto.TransactionDTO;

import java.util.List;

/**
 * Service Interface for managing croupiers.
 */
public interface RegistrationService {

    void registerPlayer(RegistrationDTO registrationDTO);

}
