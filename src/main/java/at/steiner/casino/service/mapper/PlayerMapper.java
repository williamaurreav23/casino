package at.steiner.casino.service.mapper;

import at.steiner.casino.domain.*;
import at.steiner.casino.service.dto.PlayerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Player} and its DTO {@link PlayerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlayerMapper extends EntityMapper<PlayerDTO, Player> {


    @Mapping(target = "playerMoneyTransactions", ignore = true)
    @Mapping(target = "removePlayerMoneyTransaction", ignore = true)
    @Mapping(target = "playerStockTransactions", ignore = true)
    @Mapping(target = "removePlayerStockTransaction", ignore = true)
    @Mapping(target = "playerStocks", ignore = true)
    @Mapping(target = "removePlayerStock", ignore = true)
    Player toEntity(PlayerDTO playerDTO);

    default Player fromId(Long id) {
        if (id == null) {
            return null;
        }
        Player player = new Player();
        player.setId(id);
        return player;
    }
}
