package unicv.poo.eventos_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/* mudanca dia 04/05/2026 */

@Data
@Entity
@Table(name = "locais") 
@NoArgsConstructor 
@AllArgsConstructor
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false )
    private String endereco;

    @Column(nullable = false)
    private int capacidade;

    @OneToMany(mappedBy = "local")
    private List<Evento> eventos;
}