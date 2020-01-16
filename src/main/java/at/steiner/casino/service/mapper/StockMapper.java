package at.steiner.casino.service.mapper;

import at.steiner.casino.domain.*;
import at.steiner.casino.service.dto.StockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Stock} and its DTO {@link StockDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StockMapper extends EntityMapper<StockDTO, Stock> {


    @Mapping(target = "playerStockTransactions", ignore = true)
    @Mapping(target = "removePlayerStockTransaction", ignore = true)
    @Mapping(target = "playerStocks", ignore = true)
    @Mapping(target = "removePlayerStock", ignore = true)
    @Mapping(target = "stockValueChanges", ignore = true)
    @Mapping(target = "removeStockValueChange", ignore = true)
    Stock toEntity(StockDTO stockDTO);

    default Stock fromId(Long id) {
        if (id == null) {
            return null;
        }
        Stock stock = new Stock();
        stock.setId(id);
        return stock;
    }
}
