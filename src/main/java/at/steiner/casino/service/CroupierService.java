package at.steiner.casino.service;

import at.steiner.casino.domain.enumeration.Transaction;
import at.steiner.casino.service.dto.PlayerDTO;
import at.steiner.casino.service.dto.PlayerMoneyTransactionDTO;
import at.steiner.casino.service.dto.TransactionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing croupiers.
 */
public interface CroupierService {


    /**
     * Get all the transaction types.
     *
     * @return the list of entities.
     */
    List<TransactionDTO> getAllTransactionTypes();

    /**
     * Sets the current players transaction type.
     *
     * @param value the value of the transaction type
     * @return whether the value could be set.
     */
    Boolean setTransactionType(Integer value);

    Transaction getTransactionType();

    PlayerMoneyTransactionDTO createTransaction(PlayerMoneyTransactionDTO playerMoneyTransactionDTO);

}
