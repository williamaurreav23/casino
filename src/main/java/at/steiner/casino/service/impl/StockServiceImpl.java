package at.steiner.casino.service.impl;

import at.steiner.casino.service.StockService;
import at.steiner.casino.domain.Stock;
import at.steiner.casino.repository.StockRepository;
import at.steiner.casino.service.dto.StockDTO;
import at.steiner.casino.service.mapper.StockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Stock}.
 */
@Service
@Transactional
public class StockServiceImpl implements StockService {

    private final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

    private final StockRepository stockRepository;

    private final StockMapper stockMapper;

    public StockServiceImpl(StockRepository stockRepository, StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    /**
     * Save a stock.
     *
     * @param stockDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StockDTO save(StockDTO stockDTO) {
        log.debug("Request to save Stock : {}", stockDTO);
        Stock stock = stockMapper.toEntity(stockDTO);
        stock = stockRepository.save(stock);
        return stockMapper.toDto(stock);
    }

    /**
     * Get all the stocks.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockDTO> findAll() {
        log.debug("Request to get all Stocks");
        return stockRepository.findAll().stream()
            .map(stockMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockDTO> findOne(Long id) {
        log.debug("Request to get Stock : {}", id);
        return stockRepository.findById(id)
            .map(stockMapper::toDto);
    }

    /**
     * Delete the stock by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stock : {}", id);
        stockRepository.deleteById(id);
    }
}
