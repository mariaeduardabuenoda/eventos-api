package unicv.poo.eventos_api.dto;

public record ParticipanteResponseDto(
    Long id,
    String nome,
    String email,
    String telefone
) {
}