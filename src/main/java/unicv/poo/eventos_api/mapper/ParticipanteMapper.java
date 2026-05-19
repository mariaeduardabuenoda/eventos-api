package unicv.poo.eventos_api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import unicv.poo.eventos_api.dto.ParticipanteRequestDto;
import unicv.poo.eventos_api.dto.ParticipanteResponseDto;
import unicv.poo.eventos_api.entity.Participante;

@Mapper(componentModel = "spring")
public interface ParticipanteMapper {

    ParticipanteResponseDto toDTO(Participante participante);

    Participante toEntity(ParticipanteRequestDto participanteDTO);

    List<ParticipanteResponseDto> toDTOList(List<Participante> participantes);

}