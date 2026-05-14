package unicv.poo.eventos_api.dto;

import java.time.LocalDateTime;

public record InscricaoResponseDTO(
        Long id,
        Long participanteId,
        Long eventoId,
        LocalDateTime dataInscricao,
        String status) {
}
