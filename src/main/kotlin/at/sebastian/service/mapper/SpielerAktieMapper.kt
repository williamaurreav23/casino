package at.sebastian.service.mapper

import at.sebastian.domain.SpielerAktie
import at.sebastian.service.dto.SpielerAktieDTO
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

/**
 * Mapper for the entity [SpielerAktie] and its DTO [SpielerAktieDTO].
 */
@Mapper(componentModel = "spring", uses = [SpielerMapper::class, AktieMapper::class])
interface SpielerAktieMapper :
    EntityMapper<SpielerAktieDTO, SpielerAktie> {
    @Mappings(
        Mapping(source = "spieler.id", target = "spielerId"),
        Mapping(source = "aktie.id", target = "aktieId")
    )
    override fun toDto(entity: SpielerAktie): SpielerAktieDTO

    @Mappings(
        Mapping(source = "spielerId", target = "spieler"),
        Mapping(source = "aktieId", target = "aktie")
    )
    override fun toEntity(dto: SpielerAktieDTO): SpielerAktie

    fun fromId(id: Long?) = id?.let { SpielerAktie(it) }
}
