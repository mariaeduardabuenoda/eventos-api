package unicv.poo.eventos_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/* mudanca dia 04/05/2026 */
public record LocalRequestDTO(
    Long id,

    @NotBlank(message = "O nome é obrigatório")
    String nome,

    @NotBlank(message = "O endereço é obrigatório")
    String endereco,

    @NotNull(message = "A capacidade é obrigatória")
    @Min(value = 1, message = "A capacidade deve ser no mínimo 1")
    Integer capacidade
) {}