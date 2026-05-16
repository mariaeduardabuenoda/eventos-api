package unicv.poo.eventos_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unicv.poo.eventos_api.dto.IngressoRequestDto;
import unicv.poo.eventos_api.dto.IngressoResponseDto;
import unicv.poo.eventos_api.service.IngressoService;

@RestController
@RequestMapping("/ingressos")
@RequiredArgsConstructor
public class IngressoController {

    private final IngressoService ingressoService;

    @PostMapping
    public ResponseEntity<IngressoResponseDto> criar(@RequestBody @Valid IngressoRequestDto requestDto) {
        IngressoResponseDto responseDto = ingressoService.salvar(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

}
