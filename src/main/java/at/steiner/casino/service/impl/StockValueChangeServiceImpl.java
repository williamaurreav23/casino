package at.steiner.casino.service.impl;

import at.steiner.casino.domain.Stock;
import at.steiner.casino.repository.StockRepository;
import at.steiner.casino.service.StockValueChangeService;
import at.steiner.casino.domain.StockValueChange;
import at.steiner.casino.repository.StockValueChangeRepository;
import at.steiner.casino.service.dto.StockValueChangeDTO;
import at.steiner.casino.service.mapper.StockValueChangeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link StockValueChange}.
 */
@Service
@Transactional
public class StockValueChangeServiceImpl implements StockValueChangeService {

    private final Logger log = LoggerFactory.getLogger(StockValueChangeServiceImpl.class);

    private final StockValueChangeRepository stockValueChangeRepository;
    private final StockRepository stockRepository;

    private final StockValueChangeMapper stockValueChangeMapper;

    public StockValueChangeServiceImpl(StockValueChangeRepository stockValueChangeRepository, StockRepository stockRepository, StockValueChangeMapper stockValueChangeMapper) {
        this.stockValueChangeRepository = stockValueChangeRepository;
        this.stockRepository = stockRepository;
        this.stockValueChangeMapper = stockValueChangeMapper;
    }

    /**
     * Save a stockValueChange.
     *
     * @param stockValueChangeDTOs the entity to save.
     * @return the persisted entity.
     */
    @Override
    @Transactional
    public List<StockValueChangeDTO> save(List<StockValueChangeDTO> stockValueChangeDTOs) {
        log.debug("Request to save StockValueChange : {}", stockValueChangeDTOs);
        List<StockValueChange> stockValueChanges = stockValueChangeMapper.toEntity(stockValueChangeDTOs);
        stockValueChanges = stockValueChangeRepository.saveAll(stockValueChanges);

        for (StockValueChange stockValueChange : stockValueChanges) {
            Stock stock = stockValueChange.getStock();
            stock.setValue(stockValueChange.getValue());
            stockRepository.save(stock);
        }

        return stockValueChangeMapper.toDto(stockValueChanges);
    }

    /**
     * Get all the stockValueChanges.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockValueChangeDTO> findAll() {
        log.debug("Request to get all StockValueChanges");
        return stockValueChangeRepository.findAll().stream()
            .map(stockValueChangeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stockValueChange by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockValueChangeDTO> findOne(Long id) {
        log.debug("Request to get StockValueChange : {}", id);
        return stockValueChangeRepository.findById(id)
            .map(stockValueChangeMapper::toDto);
    }

    /**
     * Delete the stockValueChange by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockValueChange : {}", id);
        stockValueChangeRepository.deleteById(id);
    }
}
