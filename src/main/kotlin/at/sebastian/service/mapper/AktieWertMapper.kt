package at.sebastian.service.mapper

import at.sebastian.domain.AktieWert
import at.sebastian.service.dto.AktieWertDTO
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

/**
 * Mapper for the entity [AktieWert] and its DTO [AktieWertDTO].
 */
@Mapper(componentModel = "spring", uses = [AktieMapper::class])
interface AktieWertMapper :
    EntityMapper<AktieWertDTO, AktieWert> {
    @Mappings(
        Mapping(source = "aktie.id", target = "aktieId")
    )
    override fun toDto(entity: AktieWert): AktieWertDTO

    @Mappings(
        Mapping(source = "aktieId", target = "aktie")
    )
    override fun toEntity(dto: AktieWertDTO): AktieWert

    fun fromId(id: Long?) = id?.let { AktieWert(it) }
}
