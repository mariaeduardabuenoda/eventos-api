package unicv.poo.eventos_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import unicv.poo.eventos_api.dto.InscricaoRequestDTO;
import unicv.poo.eventos_api.dto.InscricaoResponseDTO;
import unicv.poo.eventos_api.entity.Inscricao;

@Mapper(componentModel = "spring")
public interface InscricaoMapper {

    @Mapping(target = "participante.id", source = "participanteId")
    @Mapping(target = "evento.id", source = "eventoId")
    Inscricao toEntity(InscricaoRequestDTO dto);

    @Mapping(target = "participanteId", source = "participante.id")
    @Mapping(target = "eventoId", source = "evento.id")
    InscricaoResponseDTO toResponseDto(Inscricao entity);

}
