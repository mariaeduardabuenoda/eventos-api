package unicv.poo.eventos_api.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicv.poo.eventos_api.dto.IngressoRequestDto;
import unicv.poo.eventos_api.dto.IngressoResponseDto;
import unicv.poo.eventos_api.entity.Evento;
import unicv.poo.eventos_api.entity.Ingresso;
import unicv.poo.eventos_api.mapper.IngressoMapper;
import unicv.poo.eventos_api.repository.EventoRepository;
import unicv.poo.eventos_api.repository.IngressoRepository;

@Service
@RequiredArgsConstructor
public class IngressoService {

    private final IngressoRepository ingressoRepository;
    private final EventoRepository eventoRepository;
    private final IngressoMapper ingressoMapper;

    @Transactional
    public IngressoResponseDto salvar(IngressoRequestDto ingressoRequestDto) {

        Ingresso ingresso = ingressoMapper.toEntity(ingressoRequestDto);

        Evento evento = eventoRepository.findById(ingressoRequestDto.eventoId())
                .orElseThrow((() -> new EntityNotFoundException("Evento não existe.")));

        ingresso.setEvento(evento);

        return ingressoMapper.toResponseDto(ingressoRepository.save(ingresso));
    };

}
