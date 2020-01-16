package at.steiner.casino.service.mapper;

import at.steiner.casino.domain.*;
import at.steiner.casino.service.dto.StockValueChangeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StockValueChange} and its DTO {@link StockValueChangeDTO}.
 */
@Mapper(componentModel = "spring", uses = {StockMapper.class})
public interface StockValueChangeMapper extends EntityMapper<StockValueChangeDTO, StockValueChange> {

    @Mapping(source = "stock.id", target = "stockId")
    StockValueChangeDTO toDto(StockValueChange stockValueChange);

    @Mapping(source = "stockId", target = "stock")
    StockValueChange toEntity(StockValueChangeDTO stockValueChangeDTO);

    default StockValueChange fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockValueChange stockValueChange = new StockValueChange();
        stockValueChange.setId(id);
        return stockValueChange;
    }
}
