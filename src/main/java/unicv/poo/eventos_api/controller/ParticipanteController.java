package unicv.poo.eventos_api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unicv.poo.eventos_api.dto.ParticipanteRequestDto;
import unicv.poo.eventos_api.dto.ParticipanteResponseDto;
import unicv.poo.eventos_api.service.ParticipanteService;
import unicv.poo.eventos_api.service.utils.ApiResponse;
import unicv.poo.eventos_api.service.utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Participantes", description = "Endpoints para gerenciamento de participantes.")
@RestController
@RequestMapping("/api/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    @Operation(summary = "Listar todos", description = "Retorna lista de todos os participantes cadastrados.")
    @GetMapping
    public ResponseEntity<ApiResponse<?>> listarParticipantes() {
        try {
            List<ParticipanteResponseDto> participantes = participanteService.listarTodos();
            return ResponseEntity.ok(new ApiResponse<>(participantes));
        } catch (RuntimeException e) {
            ErrorResponse error = new ErrorResponse("ERRO", e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse<>(error));
        }
    }

    @Operation(summary = "Buscar por ID", description = "Retorna um participante específico pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> buscarPorId(@PathVariable Long id) {
        try {
            ParticipanteResponseDto participanteDTO = participanteService.buscarPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(participanteDTO));
        } catch (RuntimeException e) {
            ErrorResponse error = new ErrorResponse("ERRO", e.getMessage());
            return ResponseEntity.status(404).body(new ApiResponse<>(error));
        }
    }

    @Operation(summary = "Criar novo", description = "Cria um novo participante com validações.")
    @PostMapping
    public ResponseEntity<ApiResponse<?>> criar(@Valid @RequestBody ParticipanteRequestDto participanteDTO) {
        try {
            ParticipanteResponseDto salvo = participanteService.salvar(participanteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(salvo));
        } catch (RuntimeException e) {
            ErrorResponse error = new ErrorResponse("ERRO", e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse<>(error));
        }
    }

    @Operation(summary = "Atualizar", description = "Atualiza um participante existente pelo ID.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> atualizar(@PathVariable Long id,
            @Valid @RequestBody ParticipanteRequestDto participanteDTO) {
        try {
            ParticipanteResponseDto atualizado = participanteService.atualizar(id, participanteDTO);
            return ResponseEntity.ok(new ApiResponse<>(atualizado));
        } catch (RuntimeException e) {
            ErrorResponse error = new ErrorResponse("ERRO", e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse<>(error));
        }
    }

    @Operation(summary = "Deletar", description = "Deleta um participante pelo ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deletar(@PathVariable Long id) {
        try {
            participanteService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            ErrorResponse error = new ErrorResponse("ERRO", e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse<>(error));
        }
    }
}