package unicv.poo.eventos_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import unicv.poo.eventos_api.dto.EventoRequestDto;
import unicv.poo.eventos_api.dto.EventoResponseDto;
import unicv.poo.eventos_api.entity.Evento;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventoMapper {

    @Mapping(source = "local.id", target = "localId")
    EventoResponseDto toResponseDto(Evento evento);

    @Mapping(target = "local", ignore = true)
    Evento toEntity(EventoRequestDto eventoRequestDto);

    List<EventoResponseDto> toResponseDtoList(List<Evento> eventoList);

}