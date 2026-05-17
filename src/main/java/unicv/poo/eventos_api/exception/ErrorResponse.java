package unicv.poo.eventos_api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        LocalDateTime timestamp,
        Integer status,
        String erro,
        String mensagem,
        String path,
        List<CampoErro> campos
) {
    public record CampoErro(String campo, String mensagem) {}
}
