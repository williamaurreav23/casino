package at.steiner.casino.service.mapper;

import at.steiner.casino.domain.*;
import at.steiner.casino.service.dto.PlayerStockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlayerStock} and its DTO {@link PlayerStockDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlayerMapper.class, StockMapper.class})
public interface PlayerStockMapper extends EntityMapper<PlayerStockDTO, PlayerStock> {

    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "stock.id", target = "stockId")
    PlayerStockDTO toDto(PlayerStock playerStock);

    @Mapping(source = "playerId", target = "player")
    @Mapping(source = "stockId", target = "stock")
    PlayerStock toEntity(PlayerStockDTO playerStockDTO);

    default PlayerStock fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlayerStock playerStock = new PlayerStock();
        playerStock.setId(id);
        return playerStock;
    }
}
