package unicv.poo.eventos_api.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoResponseDto(
        Long id,
        String nome,
        String descricao,
        LocalDate dataEvento,
        LocalTime horario,
        Integer capacidade,
        Long localId,
        String status
) {
}
