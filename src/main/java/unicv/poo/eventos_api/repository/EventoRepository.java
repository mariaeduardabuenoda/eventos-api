package unicv.poo.eventos_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
<<<<<<< Updated upstream
import unicv.poo.eventos_api.entity.Evento;
=======

import unicv.poo.eventos_api.entity.Evento; // Ajustado para Evento
>>>>>>> Stashed changes

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

<<<<<<< Updated upstream
    // Ajustado para 'LocalId' e 'Capacidade', combinando com a sua Entity Evento
    boolean existsByLocalIdAndCapacidadeGreaterThan(Long localId, int capacidade);
    
}
=======
    // O Spring Data JPA vai criar a lógica sozinho baseado neste nome
    boolean existsByLocalIdAndParticipantesMaxGreaterThan(Long localId, int capacidade);
    
}

>>>>>>> Stashed changes
