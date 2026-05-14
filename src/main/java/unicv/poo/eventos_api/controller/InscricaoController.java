package unicv.poo.eventos_api.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import unicv.poo.eventos_api.dto.InscricaoRequestDTO;
import unicv.poo.eventos_api.dto.InscricaoResponseDTO;
import unicv.poo.eventos_api.mapper.InscricaoMapper;
import unicv.poo.eventos_api.service.InscricaoService;
import unicv.poo.eventos_api.entity.Inscricao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/inscricao")
public class InscricaoController {

    private final InscricaoService service;
    private final InscricaoMapper mapper;

    public InscricaoController(InscricaoService service, InscricaoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InscricaoResponseDTO realizarInscricao(@RequestBody @Valid InscricaoRequestDTO RequestDto) {
        Inscricao entidade = mapper.toEntity(RequestDto);

        Inscricao salva = service.realizarInscricao(entidade);

        return mapper.toResponseDto(salva);
    }

    @GetMapping
    public List<InscricaoResponseDTO> listarTodas() {
        return service.listarTodas().stream().map(mapper::toResponseDto).toList();
    }

}
