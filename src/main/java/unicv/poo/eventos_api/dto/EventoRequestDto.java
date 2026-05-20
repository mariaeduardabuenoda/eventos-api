package unicv.poo.eventos_api.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import unicv.poo.eventos_api.enums.StatusEventoEnum;
import unicv.poo.eventos_api.validation.ValueOfEnum;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoRequestDto(

        @NotBlank(message = "O nome do evento é obrigatório.")
        String nome,

        @NotBlank(message = "A descrição do evento é obrigatória.")
        String descricao,

        @NotNull(message = "A data do evento é obrigatória.")
        @FutureOrPresent(message = "A data do evento não pode ser no passado.")
        LocalDate dataEvento,

        @NotNull(message = "O horário do evento é obrigatório.")
        LocalTime horario,

        @NotNull(message = "A capacidade não pode ser nula.")
        @Positive(message = "A capacidade deve ser maior que zero.")
        Integer capacidade,

        @NotNull(message = "O id do local é obrigatório.")
        @Positive(message = "O id do local deve ser válido.")
        Long localId,

        @NotNull(message = "O status é obrigatório.")
        @ValueOfEnum(enumClass = StatusEventoEnum.class)
        String status

) {
}
