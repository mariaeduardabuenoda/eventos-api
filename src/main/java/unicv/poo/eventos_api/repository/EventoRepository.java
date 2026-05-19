package unicv.poo.eventos_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unicv.poo.eventos_api.entity.Evento; // Ajustado para Evento

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    // O Spring Data JPA vai criar a lógica sozinho baseado neste nome
    boolean existsByLocalIdAndParticipantesMaxGreaterThan(Long localId, int capacidade);
    
}

import unicv.poo.eventos_api.entity.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
}

