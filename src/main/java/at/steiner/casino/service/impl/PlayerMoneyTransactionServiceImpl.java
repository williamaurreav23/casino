package at.steiner.casino.service.impl;

import at.steiner.casino.service.PlayerMoneyTransactionService;
import at.steiner.casino.domain.PlayerMoneyTransaction;
import at.steiner.casino.repository.PlayerMoneyTransactionRepository;
import at.steiner.casino.service.dto.PlayerMoneyTransactionDTO;
import at.steiner.casino.service.mapper.PlayerMoneyTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PlayerMoneyTransaction}.
 */
@Service
@Transactional
public class PlayerMoneyTransactionServiceImpl implements PlayerMoneyTransactionService {

    private final Logger log = LoggerFactory.getLogger(PlayerMoneyTransactionServiceImpl.class);

    private final PlayerMoneyTransactionRepository playerMoneyTransactionRepository;

    private final PlayerMoneyTransactionMapper playerMoneyTransactionMapper;

    public PlayerMoneyTransactionServiceImpl(PlayerMoneyTransactionRepository playerMoneyTransactionRepository, PlayerMoneyTransactionMapper playerMoneyTransactionMapper) {
        this.playerMoneyTransactionRepository = playerMoneyTransactionRepository;
        this.playerMoneyTransactionMapper = playerMoneyTransactionMapper;
    }

    /**
     * Save a playerMoneyTransaction.
     *
     * @param playerMoneyTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PlayerMoneyTransactionDTO save(PlayerMoneyTransactionDTO playerMoneyTransactionDTO) {
        log.debug("Request to save PlayerMoneyTransaction : {}", playerMoneyTransactionDTO);
        PlayerMoneyTransaction playerMoneyTransaction = playerMoneyTransactionMapper.toEntity(playerMoneyTransactionDTO);
        playerMoneyTransaction = playerMoneyTransactionRepository.save(playerMoneyTransaction);
        return playerMoneyTransactionMapper.toDto(playerMoneyTransaction);
    }

    /**
     * Get all the playerMoneyTransactions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PlayerMoneyTransactionDTO> findAllByPlayer(Long playerId) {
        log.debug("Request to get all PlayerMoneyTransactions for Player : {}", playerId);
        return playerMoneyTransactionRepository.findAllByPlayerIdOrderByTimeAsc(playerId).stream()
            .map(playerMoneyTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one playerMoneyTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlayerMoneyTransactionDTO> findOne(Long id) {
        log.debug("Request to get PlayerMoneyTransaction : {}", id);
        return playerMoneyTransactionRepository.findById(id)
            .map(playerMoneyTransactionMapper::toDto);
    }

    /**
     * Delete the playerMoneyTransaction by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlayerMoneyTransaction : {}", id);
        playerMoneyTransactionRepository.deleteById(id);
    }
}
