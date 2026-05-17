package unicv.poo.eventos_api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "inscricoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participante_id", nullable = false)
    private Participante participante;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(nullable = false)
    private LocalDateTime dataInscricao;

    @Column(nullable = false)
    private String status;
}
