package unicv.poo.eventos_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unicv.poo.eventos_api.dto.LocalRequestDTO;
import unicv.poo.eventos_api.dto.LocalResponseDTO;
import unicv.poo.eventos_api.service.LocalService;

import java.util.List;

@Tag(name = "Locais", description = "Endpoints para gerenciamento de locais")
@RestController
@RequestMapping("/api/locais")
public class LocalController {

    @Autowired
    private LocalService localService;

    @Operation(summary = "Lista todos os locais", description = "Retorna uma lista com todos os locais cadastrados")
    @GetMapping
    public ResponseEntity<List<LocalResponseDTO>> listarLocais() {
        List<LocalResponseDTO> locais = localService.listarTodos();
        return ResponseEntity.ok(locais);
    }

    @Operation(summary = "Busca um local por ID", description = "Retorna os detalhes de um local especifico")
    @GetMapping("/{id}")
    public ResponseEntity<LocalResponseDTO> buscarPorId(@PathVariable Long id) {
        LocalResponseDTO local = localService.buscarPorId(id);
        return ResponseEntity.ok(local);
    }

    @Operation(summary = "Registra um novo local", description = "Cadastra um novo local no sistema")
    @PostMapping
    public ResponseEntity<LocalResponseDTO> registrarLocal(@Valid @RequestBody LocalRequestDTO localRequestDTO) {
        LocalResponseDTO novoLocal = localService.salvar(localRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoLocal);
    }

    @Operation(summary = "Atualiza um local", description = "Atualiza um local do sistema pelo ID")
    @PutMapping("/{id}")
    public ResponseEntity<LocalResponseDTO> atualizarLocal(@PathVariable Long id, @Valid @RequestBody LocalRequestDTO localRequestDTO) {
        LocalResponseDTO localAtualizado = localService.atualizar(id, localRequestDTO);
        return ResponseEntity.ok(localAtualizado);
    }

    @Operation(summary = "Deleta um local", description = "Remove um local do sistema pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLocal(@PathVariable Long id) {
        localService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}