package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Indice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long indice;
    @Enumerated(EnumType.STRING)
    private Visualizacao visualizacao;


    public Indice(Long indice, Visualizacao visualizacao) {
        this.indice = indice;
        this.visualizacao = visualizacao;
    }
}
