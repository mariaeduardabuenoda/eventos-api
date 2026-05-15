package unicv.poo.eventos_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicv.poo.eventos_api.entity.Ingresso;

@Repository
public interface EventoRepository extends JpaRepository<Ingresso, Long> {
    static boolean existsByLocalIdAndParticipantesMaxGreaterThan(Long localId, int capacidade) {
        throw new UnsupportedOperationException("Unimplemented method 'existsByLocalIdAndParticipantesMaxGreaterThan'");
    }
}
