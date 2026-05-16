package unicv.poo.eventos_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import unicv.poo.eventos_api.dto.IngressoRequestDto;
import unicv.poo.eventos_api.dto.IngressoResponseDto;
import unicv.poo.eventos_api.entity.Ingresso;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngressoMapper {

    @Mapping(source = "evento.id", target = "eventoId")
    IngressoResponseDto toResponseDto(Ingresso ingresso);

    @Mapping(target = "evento", ignore = true)
    Ingresso toEntity(IngressoRequestDto ingressoRequestDto);

    List<IngressoResponseDto> toResponseDtoList(List<Ingresso> ingressoList);

}
