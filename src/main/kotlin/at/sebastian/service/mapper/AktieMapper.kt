package at.sebastian.service.mapper

import at.sebastian.domain.Aktie
import at.sebastian.service.dto.AktieDTO

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

/**
 * Mapper for the entity [Aktie] and its DTO [AktieDTO].
 */
@Mapper(componentModel = "spring", uses = [])
interface AktieMapper :
    EntityMapper<AktieDTO, Aktie> {

    @Mappings(
        Mapping(target = "spielerAkties", ignore = true),
        Mapping(target = "removeSpielerAktie", ignore = true),
        Mapping(target = "aktieWerts", ignore = true),
        Mapping(target = "removeAktieWert", ignore = true)
    )
    override fun toEntity(dto: AktieDTO): Aktie

    fun fromId(id: Long?) = id?.let { Aktie(it) }
}
