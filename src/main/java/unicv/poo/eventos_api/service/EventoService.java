package unicv.poo.eventos_api.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicv.poo.eventos_api.dto.EventoRequestDto;
import unicv.poo.eventos_api.dto.EventoResponseDto;
import unicv.poo.eventos_api.entity.Evento;
import unicv.poo.eventos_api.entity.Local;
import unicv.poo.eventos_api.exception.RegraNegocioException;
import unicv.poo.eventos_api.mapper.EventoMapper;
import unicv.poo.eventos_api.repository.EventoRepository;
import unicv.poo.eventos_api.repository.InscricaoRepository;
import unicv.poo.eventos_api.repository.LocalRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final EventoMapper eventoMapper;
    private final InscricaoRepository inscricaoRepository;
    private final LocalRepository localRepository;

    @Transactional
    public EventoResponseDto salvar(EventoRequestDto eventoRequestDto) {
        Evento evento = eventoMapper.toEntity(eventoRequestDto);
        Local local = localRepository.findById(eventoRequestDto.localId())
                .orElseThrow(() -> new EntityNotFoundException("Local não encontrado."));
        if (eventoRequestDto.capacidade() > local.getCapacidade()) {
            throw new RegraNegocioException("A capacidade do evento não pode ser maior que a capacidade do local.");
        }
        boolean horarioOcupado = local.getEventos().stream()
                .anyMatch(e -> e.getDataEvento().equals(eventoRequestDto.dataEvento())
                        && e.getHorario().equals(eventoRequestDto.horario()));
        if (horarioOcupado) {
            throw new RegraNegocioException("Já existe um evento neste local na mesma data e horário.");
        }
        evento.setLocal(local);
        return eventoMapper.toResponseDto(eventoRepository.save(evento));
    }

    public List<EventoResponseDto> listarTodos() {
        List<Evento> eventos = eventoRepository.findAll();
        return eventoMapper.toResponseDtoList(eventos);
    }

    public EventoResponseDto buscarPorId(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento informado não existe."));
        return eventoMapper.toResponseDto(evento);
    }

    @Transactional
    public EventoResponseDto atualizar(Long id, EventoRequestDto eventoRequestDto) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento informado não existe."));
        Local local = localRepository.findById(eventoRequestDto.localId())
                .orElseThrow(() -> new EntityNotFoundException("Local não encontrado."));
        if (eventoRequestDto.capacidade() > local.getCapacidade()) {
            throw new RegraNegocioException("A capacidade do evento não pode ser maior que a capacidade do local.");
        }
        boolean horarioOcupado = local.getEventos().stream()
                .filter(e -> !e.getId().equals(id))
                .anyMatch(e -> e.getDataEvento().equals(eventoRequestDto.dataEvento())
                        && e.getHorario().equals(eventoRequestDto.horario()));
        if (horarioOcupado) {
            throw new RegraNegocioException("Já existe um evento neste local na mesma data e horário.");
        }
        long inscritosAtuais = inscricaoRepository.countByEventoIdAndStatus(id, "Confirmada");
        if (eventoRequestDto.capacidade() < inscritosAtuais) {
            throw new RegraNegocioException("A capacidade não pode ser menor que as inscrições já confirmadas (" + inscritosAtuais + ").");
        }

        evento.setNome(eventoRequestDto.nome());
        evento.setDescricao(eventoRequestDto.descricao());
        evento.setDataEvento(eventoRequestDto.dataEvento());
        evento.setHorario(eventoRequestDto.horario());
        evento.setCapacidade(eventoRequestDto.capacidade());
        evento.setStatus(eventoRequestDto.status());
        evento.setLocal(local);

        return eventoMapper.toResponseDto(eventoRepository.save(evento));
    }

    public void deletar(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new EntityNotFoundException("Evento informado não existe.");
        }
        eventoRepository.deleteById(id);
    }
}
