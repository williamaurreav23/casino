package at.steiner.casino.service.mapper;

import at.steiner.casino.domain.*;
import at.steiner.casino.service.dto.PlayerStockTransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlayerStockTransaction} and its DTO {@link PlayerStockTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlayerMapper.class, StockMapper.class})
public interface PlayerStockTransactionMapper extends EntityMapper<PlayerStockTransactionDTO, PlayerStockTransaction> {

    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "stock.id", target = "stockId")
    PlayerStockTransactionDTO toDto(PlayerStockTransaction playerStockTransaction);

    @Mapping(source = "playerId", target = "player")
    @Mapping(source = "stockId", target = "stock")
    PlayerStockTransaction toEntity(PlayerStockTransactionDTO playerStockTransactionDTO);

    default PlayerStockTransaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlayerStockTransaction playerStockTransaction = new PlayerStockTransaction();
        playerStockTransaction.setId(id);
        return playerStockTransaction;
    }
}
