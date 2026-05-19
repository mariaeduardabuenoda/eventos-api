package unicv.poo.eventos_api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicv.poo.eventos_api.entity.Evento;
import unicv.poo.eventos_api.entity.Inscricao;
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

    @Transactional
    public Inscricao realizarInscricao(Inscricao inscricao) {
        if (inscricao == null || inscricao.getEvento() == null || inscricao.getParticipante() == null) {
            throw new IllegalArgumentException("Dados da inscrição invalidos.");
        }

        Long eventoId = inscricao.getEvento().getId();
        Long participanteId = inscricao.getParticipante().getId();

        if (eventoId == null || participanteId == null) {
            throw new IllegalArgumentException("O ID do evento e do participante não podem ser nulos.");
        }

        Evento eventoCompleto = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado."));

        LocalDate agora = LocalDate.now();

        if (eventoCompleto.getDataEvento() != null && agora.isAfter(eventoCompleto.getDataEvento())) {
            throw new RuntimeException("Não é possível se inscrever em um evento que já iniciou ou ocorreu.");
        }

        boolean jaInscrito = repository.existsByParticipanteIdAndEventoIdAndStatus(participanteId, eventoId, "CONFIRMADA");
        if (jaInscrito) {
            throw new RuntimeException("Este participante já está inscrito neste evento.");
        }

        int capacidadeMaxima = eventoCompleto.getLocal().getCapacidade();
        long totalInscritos = repository.countByEventoIdAndStatus(eventoId, "CONFIRMADA");

        if (totalInscritos >= capacidadeMaxima) {
            throw new RuntimeException("Desculpe, esse evento já atingiu a capacidade máxima de "
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
            throw new IllegalArgumentException("O ID da inscrição não pode ser nulo.");
        }

        Inscricao inscricao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscrição não encontrada."));

        LocalDate hoje = LocalDate.now();
        
        if (inscricao.getEvento().getDataEvento() != null && inscricao.getEvento().getDataEvento().isBefore(hoje)) {
            throw new RuntimeException("Não é possível cancelar a inscrição de um evento que já ocorreu.");
        }

        inscricao.setStatus("CANCELADA");
        return repository.save(inscricao);
    }
}