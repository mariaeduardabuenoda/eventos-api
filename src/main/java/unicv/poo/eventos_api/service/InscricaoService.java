package unicv.poo.eventos_api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import unicv.poo.eventos_api.entity.Evento;
import unicv.poo.eventos_api.entity.Inscricao;
import unicv.poo.eventos_api.exception.RegraNegocioException;
import unicv.poo.eventos_api.repository.InscricaoRepository;
import unicv.poo.eventos_api.repository.EventoRepository;

@Service
public class InscricaoService {

    private final InscricaoRepository repository;
    private final EventoRepository eventoRepository;

    public InscricaoService(InscricaoRepository repository, EventoRepository eventoRepository) {
        this.repository = repository;
        this.eventoRepository = eventoRepository;
    }

    public List<Inscricao> listarTodas() {
        return repository.findAll();
    }

    public Inscricao buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Inscrição não encontrada."));
    }

    public List<Inscricao> listarPorEvento(Long eventoId) {
        return repository.findByEventoId(eventoId);
    }

    @Transactional
    public Inscricao realizarInscricao(Inscricao inscricao) {
        if (inscricao == null || inscricao.getEvento() == null || inscricao.getParticipante() == null) {
            throw new RegraNegocioException("Dados da inscrição invalidos.");
        }

        Long eventoId = inscricao.getEvento().getId();
        Long participanteId = inscricao.getParticipante().getId();

        if (eventoId == null || participanteId == null) {
            throw new RegraNegocioException("O ID do evento e do participante não podem ser nulos.");
        }

        Evento eventoCompleto = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado."));

        LocalDate agora = LocalDate.now();

        if (eventoCompleto.getDataEvento() != null && agora.isAfter(eventoCompleto.getDataEvento())) {
            throw new RegraNegocioException("Não é possível se inscrever em um evento que já iniciou ou ocorreu.");
        }

        // Aguardando o outro desenvolvedor adicionar o campo status em Evento
        // if ("CANCELADO".equalsIgnoreCase(eventoCompleto.getStatus())) {
        // throw new RegraNegocioException("Não é possível se inscrever em um evento que
        // foi cancelado.");
        // }

        boolean jaInscrito = repository.existsByParticipanteIdAndEventoIdAndStatus(participanteId, eventoId,
                "CONFIRMADA");
        if (jaInscrito) {
            throw new RegraNegocioException("Este participante já está inscrito neste evento.");
        }

        int capacidadeMaxima = eventoCompleto.getLocal().getCapacidade();
        long totalInscritos = repository.countByEventoIdAndStatus(eventoId, "CONFIRMADA");

        if (totalInscritos >= capacidadeMaxima) {
            throw new RegraNegocioException("Desculpe, esse evento já atingiu a capacidade máxima de "
                    + capacidadeMaxima + " vagas.");
        }

        inscricao.setEvento(eventoCompleto);
        inscricao.setDataInscricao(LocalDateTime.now());
        inscricao.setStatus("CONFIRMADA");

        return repository.save(inscricao);
    }

    @Transactional
    public Inscricao cancelarInscricao(Long id) {
        if (id == null) {
            throw new RegraNegocioException("O ID da inscrição não pode ser nulo.");
        }

        Inscricao inscricao = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscrição não encontrada."));

        LocalDate hoje = LocalDate.now();

        if (inscricao.getEvento().getDataEvento() != null && inscricao.getEvento().getDataEvento().isBefore(hoje)) {
            throw new RegraNegocioException("Não é possível cancelar a inscrição de um evento que já ocorreu.");
        }

        inscricao.setStatus("CANCELADA");
        return repository.save(inscricao);
    }
}