package unicv.poo.eventos_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import unicv.poo.eventos_api.entity.Local;
import unicv.poo.eventos_api.dto.LocalRequestDTO;
import unicv.poo.eventos_api.dto.LocalResponseDTO;


@Mapper(componentModel = "spring")
public interface LocalMapper{

    LocalRequestDTO toRequestDTO(Local local);
    LocalResponseDTO toResponseDTO(Local local);

    Local toEntity(LocalRequestDTO localRequestDTO );

    List<LocalResponseDTO> toResponseDTOList(List<Local> locais);



}