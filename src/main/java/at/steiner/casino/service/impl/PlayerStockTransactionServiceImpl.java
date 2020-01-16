package at.steiner.casino.service.impl;

import at.steiner.casino.service.PlayerStockTransactionService;
import at.steiner.casino.domain.PlayerStockTransaction;
import at.steiner.casino.repository.PlayerStockTransactionRepository;
import at.steiner.casino.service.dto.PlayerStockTransactionDTO;
import at.steiner.casino.service.mapper.PlayerStockTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PlayerStockTransaction}.
 */
@Service
@Transactional
public class PlayerStockTransactionServiceImpl implements PlayerStockTransactionService {

    private final Logger log = LoggerFactory.getLogger(PlayerStockTransactionServiceImpl.class);

    private final PlayerStockTransactionRepository playerStockTransactionRepository;

    private final PlayerStockTransactionMapper playerStockTransactionMapper;

    public PlayerStockTransactionServiceImpl(PlayerStockTransactionRepository playerStockTransactionRepository, PlayerStockTransactionMapper playerStockTransactionMapper) {
        this.playerStockTransactionRepository = playerStockTransactionRepository;
        this.playerStockTransactionMapper = playerStockTransactionMapper;
    }

    /**
     * Save a playerStockTransaction.
     *
     * @param playerStockTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PlayerStockTransactionDTO save(PlayerStockTransactionDTO playerStockTransactionDTO) {
        log.debug("Request to save PlayerStockTransaction : {}", playerStockTransactionDTO);
        PlayerStockTransaction playerStockTransaction = playerStockTransactionMapper.toEntity(playerStockTransactionDTO);
        playerStockTransaction = playerStockTransactionRepository.save(playerStockTransaction);
        return playerStockTransactionMapper.toDto(playerStockTransaction);
    }

    /**
     * Get all the playerStockTransactions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PlayerStockTransactionDTO> findAll() {
        log.debug("Request to get all PlayerStockTransactions");
        return playerStockTransactionRepository.findAll().stream()
            .map(playerStockTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one playerStockTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlayerStockTransactionDTO> findOne(Long id) {
        log.debug("Request to get PlayerStockTransaction : {}", id);
        return playerStockTransactionRepository.findById(id)
            .map(playerStockTransactionMapper::toDto);
    }

    /**
     * Delete the playerStockTransaction by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlayerStockTransaction : {}", id);
        playerStockTransactionRepository.deleteById(id);
    }
}
