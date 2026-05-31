package unicv.poo.eventos_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;


public record ParticipanteRequestDto(

    @Size(min = 1, max = 100, message = "O nome deve conter entre 1 e 100 caracteres")
    @NotBlank(message = "O nome é obrigatório")
    String nome,

    @Size(min = 2, max = 100, message = "O email deve conter entre 2 e 100 caracteres")
    @Email(message = "O email deve ser válido")
    @NotBlank(message = "O email é obrigatório")
    String email,

    @Pattern(regexp = "\\d{8,15}", message = "O telefone deve conter apenas números, com no mínimo 8 e no máximo 15 dígitos")
    @NotBlank(message = "O telefone é obrigatório")
    String telefone

) {
}
