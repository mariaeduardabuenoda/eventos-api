package unicv.poo.eventos_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicv.poo.eventos_api.dto.IngressoRequestDto;
import unicv.poo.eventos_api.dto.IngressoResponseDto;
import unicv.poo.eventos_api.service.IngressoService;

import java.util.List;

@RestController
@RequestMapping("/api/ingressos")
@RequiredArgsConstructor
public class IngressoController {

    private final IngressoService ingressoService;

    @PostMapping
    public ResponseEntity<IngressoResponseDto> criar(@RequestBody @Valid IngressoRequestDto requestDto) {
        IngressoResponseDto responseDto = ingressoService.salvar(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<IngressoResponseDto>> listar() {
        List<IngressoResponseDto> responseDtos = ingressoService.listarTodos();
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngressoResponseDto> buscarPorId(@PathVariable Long id) {
        IngressoResponseDto responseDto = ingressoService.buscarPorId(id);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngressoResponseDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody IngressoRequestDto requestDto
    ) {
        IngressoResponseDto responseDto = ingressoService.atualizar(id, requestDto);
        return ResponseEntity.ok(responseDto);
    };

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ingressoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
