package at.steiner.casino.service.impl;

import at.steiner.casino.domain.Player;
import at.steiner.casino.domain.User;
import at.steiner.casino.domain.UserExtra;
import at.steiner.casino.domain.enumeration.Transaction;
import at.steiner.casino.repository.PlayerRepository;
import at.steiner.casino.repository.UserExtraRepository;
import at.steiner.casino.service.CroupierService;
import at.steiner.casino.service.PlayerService;
import at.steiner.casino.service.UserService;
import at.steiner.casino.service.dto.PlayerDTO;
import at.steiner.casino.service.dto.TransactionDTO;
import at.steiner.casino.service.mapper.PlayerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing croupiers.
 */
@Service
@Transactional
public class CroupierServiceImpl implements CroupierService {

    private final Logger log = LoggerFactory.getLogger(CroupierServiceImpl.class);

    private final UserService userService;

    private final UserExtraRepository userExtraRepository;

    public CroupierServiceImpl(UserService userService, UserExtraRepository userExtraRepository) {
        this.userService = userService;
        this.userExtraRepository = userExtraRepository;
    }

    @Override
    public List<TransactionDTO> getAllTransactionTypes() {
        log.debug("Request to get all Transaction types");

        List<TransactionDTO> transactionDTOs = new ArrayList<>();
        transactionDTOs.add(new TransactionDTO(Transaction.ROULETTE.getValue(), "Roulette"));
        transactionDTOs.add(new TransactionDTO(Transaction.BLACK_JACK.getValue(), "Black Jack"));
        transactionDTOs.add(new TransactionDTO(Transaction.POKER.getValue(), "Poker"));
        transactionDTOs.add(new TransactionDTO(Transaction.STOCK.getValue(), "Aktien"));
        transactionDTOs.add(new TransactionDTO(Transaction.OTHER.getValue(), "Andere"));
        return transactionDTOs;
    }

    @Override
    public Boolean setTransactionType(Integer value) {
        Transaction transaction = Transaction.fromValue(value);
        if (transaction.getValue() == 0) {
            return false;
        }
        Optional<User> userOpt = userService.getUserWithAuthorities();
        if (!userOpt.isPresent()) {
            return false;
        }
        Optional<UserExtra> userExtraOpt = userExtraRepository.getByUser(userOpt.get());
        if (!userExtraOpt.isPresent()) {
            return false;
        }
        UserExtra userExtra = userExtraOpt.get();
        userExtra.setTransaction(transaction);
        userExtraRepository.save(userExtra);
        return true;
    }
}
