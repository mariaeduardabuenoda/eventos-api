package unicv.poo.eventos_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import unicv.poo.eventos_api.entity.Evento; // Ajustado para Evento




@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Ajustado para 'LocalId' e 'Capacidade', combinando com a sua Entity Evento
    boolean existsByLocalIdAndCapacidadeGreaterThan(Long localId, int capacidade);
    
}


