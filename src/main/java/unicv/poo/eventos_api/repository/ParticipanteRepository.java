package unicv.poo.eventos_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unicv.poo.eventos_api.entity.Participante;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    Optional<Participante> findByEmail(String email);

    boolean existsByEmail(String email);

}
