package unicv.poo.eventos_api.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import unicv.poo.eventos_api.dto.InscricaoRequestDTO;
import unicv.poo.eventos_api.dto.InscricaoResponseDTO;
import unicv.poo.eventos_api.entity.Inscricao;
import unicv.poo.eventos_api.mapper.InscricaoMapper;
import unicv.poo.eventos_api.service.InscricaoService;


@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    private final InscricaoService service;
    private final InscricaoMapper mapper;

    public InscricaoController(InscricaoService service, InscricaoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InscricaoResponseDTO realizarInscricao(@RequestBody @Valid InscricaoRequestDTO requestDto) {
        Inscricao entidade = mapper.toEntity(requestDto);
        Inscricao salva = service.realizarInscricao(entidade);
        return mapper.toResponseDto(salva);
    }

    @GetMapping
    public List<InscricaoResponseDTO> listarTodas() {
        return service.listarTodas()
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    @GetMapping("/{id}")
    public InscricaoResponseDTO buscarPorId(@PathVariable Long id) {
        Inscricao entidade = service.buscarPorId(id);
        return mapper.toResponseDto(entidade);
    }

    @GetMapping("/evento/{eventoId}")
    public List<InscricaoResponseDTO> listarPorEvento(@PathVariable Long eventoId) {
        return service.listarPorEvento(eventoId)
        .stream()
        .map(mapper::toResponseDto)
        .toList();
    }

    @PatchMapping("/{id}/cancelar")
    public InscricaoResponseDTO cancelarInscricao(@PathVariable Long id){
        Inscricao cancelada = service.cancelarInscricao(id);
        return mapper.toResponseDto(cancelada);
    }
}