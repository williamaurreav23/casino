package at.steiner.casino.service.impl;

import at.steiner.casino.domain.Player;
import at.steiner.casino.domain.PlayerMoneyTransaction;
import at.steiner.casino.domain.User;
import at.steiner.casino.domain.UserExtra;
import at.steiner.casino.domain.enumeration.Transaction;
import at.steiner.casino.repository.PlayerMoneyTransactionRepository;
import at.steiner.casino.repository.PlayerRepository;
import at.steiner.casino.repository.UserExtraRepository;
import at.steiner.casino.service.CroupierService;
import at.steiner.casino.service.RegistrationService;
import at.steiner.casino.service.UserService;
import at.steiner.casino.service.dto.PlayerMoneyTransactionDTO;
import at.steiner.casino.service.dto.RegistrationDTO;
import at.steiner.casino.service.dto.TransactionDTO;
import at.steiner.casino.service.mapper.PlayerMapper;
import at.steiner.casino.service.mapper.PlayerMoneyTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing croupiers.
 */
@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    private final Logger log = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    private final PlayerRepository playerRepository;
    private final PlayerMoneyTransactionRepository playerMoneyTransactionRepository;

    public RegistrationServiceImpl(PlayerRepository playerRepository,
                                   PlayerMoneyTransactionRepository playerMoneyTransactionRepository) {
        this.playerRepository = playerRepository;
        this.playerMoneyTransactionRepository = playerMoneyTransactionRepository;
    }

    @Override
    @Transactional
    public void registerPlayer(RegistrationDTO registrationDTO) {
        log.debug("Request to register player : {}", registrationDTO);
        Player newPlayer = new Player();
        newPlayer.setName(registrationDTO.getName());
        newPlayer.setMoney(registrationDTO.getMoney());
        newPlayer.setIsChild(registrationDTO.isIsChild());
        newPlayer.setPassNumber(registrationDTO.getPassNumber());
        newPlayer = playerRepository.save(newPlayer);

        PlayerMoneyTransaction playerMoneyTransaction = new PlayerMoneyTransaction();
        playerMoneyTransaction.setValue(registrationDTO.getMoney());
        playerMoneyTransaction.setTransaction(Transaction.START);
        playerMoneyTransaction.setTime(Instant.now());
        playerMoneyTransaction.setPlayer(newPlayer);
        playerMoneyTransactionRepository.save(playerMoneyTransaction);
    }
}
