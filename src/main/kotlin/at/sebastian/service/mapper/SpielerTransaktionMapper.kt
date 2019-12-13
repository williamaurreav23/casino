package at.sebastian.service.mapper

import at.sebastian.domain.SpielerTransaktion
import at.sebastian.service.dto.SpielerTransaktionDTO

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

/**
 * Mapper for the entity [SpielerTransaktion] and its DTO [SpielerTransaktionDTO].
 */
@Mapper(componentModel = "spring", uses = [SpielerMapper::class])
interface SpielerTransaktionMapper :
    EntityMapper<SpielerTransaktionDTO, SpielerTransaktion> {
    @Mappings(
        Mapping(source = "spieler.id", target = "spielerId")
    )
    override fun toDto(entity: SpielerTransaktion): SpielerTransaktionDTO

    @Mappings(
        Mapping(source = "spielerId", target = "spieler")
    )
    override fun toEntity(dto: SpielerTransaktionDTO): SpielerTransaktion

    fun fromId(id: Long?) = id?.let { SpielerTransaktion(it) }
}
