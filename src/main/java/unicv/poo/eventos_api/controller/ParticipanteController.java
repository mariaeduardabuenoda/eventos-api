package unicv.poo.eventos_api.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unicv.poo.eventos_api.dto.ParticipanteRequestDto;
import unicv.poo.eventos_api.dto.ParticipanteResponseDto;
import unicv.poo.eventos_api.service.ParticipanteService;

@Tag(name = "Participantes", description = "Endpoints para gerenciamento de participantes.")
@RestController
@RequestMapping("/api/participantes")
@RequiredArgsConstructor
public class ParticipanteController {

    private final ParticipanteService participanteService;

    @Operation(summary = "Listar todos", description = "Retorna lista de todos os participantes cadastrados.")
    @GetMapping
    public ResponseEntity<List<ParticipanteResponseDto>> listarParticipantes() {
        return ResponseEntity.ok(participanteService.listarTodos());
    }

    @Operation(summary = "Buscar por ID", description = "Retorna um participante específico pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(participanteService.buscarPorId(id));
    }

    @Operation(summary = "Criar novo", description = "Cria um novo participante com validações.")
    @PostMapping
    public ResponseEntity<ParticipanteResponseDto> criar(@Valid @RequestBody ParticipanteRequestDto participanteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(participanteService.salvar(participanteDTO));
    }

    @Operation(summary = "Atualizar", description = "Atualiza um participante existente pelo ID.")
    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteResponseDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ParticipanteRequestDto participanteDTO) {
        return ResponseEntity.ok(participanteService.atualizar(id, participanteDTO));
    }

    @Operation(summary = "Deletar", description = "Deleta um participante pelo ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        participanteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}