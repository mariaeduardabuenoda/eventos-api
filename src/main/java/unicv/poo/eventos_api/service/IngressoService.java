package unicv.poo.eventos_api.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicv.poo.eventos_api.dto.IngressoRequestDto;
import unicv.poo.eventos_api.dto.IngressoResponseDto;
import unicv.poo.eventos_api.entity.Evento;
import unicv.poo.eventos_api.entity.Ingresso;
import unicv.poo.eventos_api.exception.RegraNegocioException;
import unicv.poo.eventos_api.mapper.IngressoMapper;
import unicv.poo.eventos_api.repository.EventoRepository;
import unicv.poo.eventos_api.repository.IngressoRepository;

import java.util.List;

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
                .orElseThrow((() -> new EntityNotFoundException("O evento informado não existe.")));

        int quantidadeTotal = evento.getIngressos().stream()
                .mapToInt(Ingresso::getQuantidade)
                .sum();

        if (quantidadeTotal + ingressoRequestDto.quantidade() > evento.getCapacidade()) {
            throw new RegraNegocioException("A quantidade de ingressos excede a capacidade do evento.");
        }

        ingresso.setEvento(evento);

        return ingressoMapper.toResponseDto(ingressoRepository.save(ingresso));
    };

    public List<IngressoResponseDto> listarTodos() {
        List<Ingresso> ingressos = ingressoRepository.findAll();
        return ingressos.stream()
                .map(ingressoMapper::toResponseDto)
                .toList();
    };

    public IngressoResponseDto buscarPorId(Long id) {
        Ingresso ingresso = ingressoRepository.findById(id)
                .orElseThrow((() -> new EntityNotFoundException("Ingresso informado nao existe.")));

        return ingressoMapper.toResponseDto(ingresso);
    }

    @Transactional
    public IngressoResponseDto atualizar(Long id, IngressoRequestDto ingressoRequestDto) {
        Ingresso ingresso = ingressoRepository.findById(id)
                .orElseThrow((() -> new EntityNotFoundException("Ingresso informado nao existe.")));

        Evento evento = eventoRepository.findById(ingressoRequestDto.eventoId())
                .orElseThrow((() -> new EntityNotFoundException("Evento não existe.")));

        int quantidadeTotal = evento.getIngressos().stream()
                .filter(i -> !i.getId().equals(id))
                .mapToInt(Ingresso::getQuantidade)
                .sum();

        if (quantidadeTotal + ingressoRequestDto.quantidade()  > evento.getCapacidade()) {
            throw new RegraNegocioException("A quantidade de ingressos excede a capacidade do evento.");
        }

        ingresso.setEvento(evento);
        ingresso.setTipo(ingressoRequestDto.tipo());
        ingresso.setPreco(ingressoRequestDto.preco());
        ingresso.setQuantidade(ingressoRequestDto.quantidade());

        return ingressoMapper.toResponseDto(ingressoRepository.save(ingresso));
    };

    @Transactional
    public void deletar(Long id) {

        if (!ingressoRepository.existsById(id)) {
            throw new EntityNotFoundException("Ingresso informado nao existe.");
        }

        ingressoRepository.deleteById(id);
    }

}
