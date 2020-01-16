package at.steiner.casino.service.impl;

import at.steiner.casino.service.PlayerStockService;
import at.steiner.casino.domain.PlayerStock;
import at.steiner.casino.repository.PlayerStockRepository;
import at.steiner.casino.service.dto.PlayerStockDTO;
import at.steiner.casino.service.mapper.PlayerStockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PlayerStock}.
 */
@Service
@Transactional
public class PlayerStockServiceImpl implements PlayerStockService {

    private final Logger log = LoggerFactory.getLogger(PlayerStockServiceImpl.class);

    private final PlayerStockRepository playerStockRepository;

    private final PlayerStockMapper playerStockMapper;

    public PlayerStockServiceImpl(PlayerStockRepository playerStockRepository, PlayerStockMapper playerStockMapper) {
        this.playerStockRepository = playerStockRepository;
        this.playerStockMapper = playerStockMapper;
    }

    /**
     * Save a playerStock.
     *
     * @param playerStockDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PlayerStockDTO save(PlayerStockDTO playerStockDTO) {
        log.debug("Request to save PlayerStock : {}", playerStockDTO);
        PlayerStock playerStock = playerStockMapper.toEntity(playerStockDTO);
        playerStock = playerStockRepository.save(playerStock);
        return playerStockMapper.toDto(playerStock);
    }

    /**
     * Get all the playerStocks.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PlayerStockDTO> findAll() {
        log.debug("Request to get all PlayerStocks");
        return playerStockRepository.findAll().stream()
            .map(playerStockMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one playerStock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlayerStockDTO> findOne(Long id) {
        log.debug("Request to get PlayerStock : {}", id);
        return playerStockRepository.findById(id)
            .map(playerStockMapper::toDto);
    }

    /**
     * Delete the playerStock by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlayerStock : {}", id);
        playerStockRepository.deleteById(id);
    }
}
