package at.steiner.casino.service.mapper;

import at.steiner.casino.domain.*;
import at.steiner.casino.service.dto.PlayerMoneyTransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlayerMoneyTransaction} and its DTO {@link PlayerMoneyTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlayerMapper.class})
public interface PlayerMoneyTransactionMapper extends EntityMapper<PlayerMoneyTransactionDTO, PlayerMoneyTransaction> {

    @Mapping(source = "player.id", target = "playerId")
    PlayerMoneyTransactionDTO toDto(PlayerMoneyTransaction playerMoneyTransaction);

    @Mapping(source = "playerId", target = "player")
    PlayerMoneyTransaction toEntity(PlayerMoneyTransactionDTO playerMoneyTransactionDTO);

    default PlayerMoneyTransaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlayerMoneyTransaction playerMoneyTransaction = new PlayerMoneyTransaction();
        playerMoneyTransaction.setId(id);
        return playerMoneyTransaction;
    }
}
