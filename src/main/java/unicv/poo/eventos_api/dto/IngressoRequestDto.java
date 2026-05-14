package unicv.poo.eventos_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import unicv.poo.eventos_api.enums.TipoIngressoEnum;
import unicv.poo.eventos_api.validation.ValueOfEnum;

import java.math.BigDecimal;

public record IngressoRequestDto(

        @NotNull(message = "O id do evento é obrigatório.")
        Long eventoId,

        @NotNull(message = "O tipo é obrigatório.")
        @ValueOfEnum(enumClass = TipoIngressoEnum.class)
        String tipo,

        @NotNull(message = "O preço não pode ser nulo.")
        @Positive(message = "O preço deve ser maior que zero.")
        BigDecimal preco,

        @NotNull(message = "A quantidade é obrigatória.")
        @PositiveOrZero(message = "A quantidade não pode ser negativa.")
        Integer quantidade

) {
}
