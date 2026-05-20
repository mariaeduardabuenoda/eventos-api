package unicv.poo.eventos_api.repository;

import unicv.poo.eventos_api.entity.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long>{
    List<Inscricao> findByEventoId(Long eventoId);
    long countByEventoIdAndStatus(Long eventoId, String status);

    boolean existsByParticipanteIdAndEventoIdAndStatus(Long participanteId, Long eventoId, String status);
}
