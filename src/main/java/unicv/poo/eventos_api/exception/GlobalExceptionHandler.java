package unicv.poo.eventos_api.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, "Recurso não encontrado.", request, null);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErrorResponse> handleRegraNegocio(RegraNegocioException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, "Regra de negócio violada.", request, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<ErrorResponse.CampoErro> campos = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorResponse.CampoErro(error.getField(), error.getDefaultMessage()))
                .toList();

        return buildErrorResponse(
                "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",
                HttpStatus.BAD_REQUEST,
                "Erro de Validação",
                request,
                campos
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(
                "A rota não foi encontrada.",
                HttpStatus.NOT_FOUND,
                "Recurso não encontrado",
                request,
                null
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {

        return buildErrorResponse(
                "Método não suportado para esta rota.",
                HttpStatus.METHOD_NOT_ALLOWED,
                "Método não permitido",
                request,
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Erro inesperado", ex);

        return buildErrorResponse(
                "Ocorreu um erro interno inesperado no servidor.",
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno do servidor",
                request,
                null
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            String mensagem,
            HttpStatus status,
            String erro,
            HttpServletRequest request,
            List<ErrorResponse.CampoErro> campos
    ) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                erro,
                mensagem,
                request.getRequestURI(),
                campos
        );

        return ResponseEntity.status(status).body(response);
    }

}
