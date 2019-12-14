package at.sebastian.service.mapper

import at.sebastian.domain.Spieler
import at.sebastian.service.dto.SpielerDTO
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

/**
 * Mapper for the entity [Spieler] and its DTO [SpielerDTO].
 */
@Mapper(componentModel = "spring", uses = [])
interface SpielerMapper :
    EntityMapper<SpielerDTO, Spieler> {

    @Mappings(
        Mapping(target = "spielerAkties", ignore = true),
        Mapping(target = "removeSpielerAktie", ignore = true),
        Mapping(target = "spielerTransaktions", ignore = true),
        Mapping(target = "removeSpielerTransaktion", ignore = true)
    )
    override fun toEntity(dto: SpielerDTO): Spieler

    fun fromId(id: Long?) = id?.let { Spieler(it) }
}
