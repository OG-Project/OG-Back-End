package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValorPropriedadeTarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Propriedade propriedade;
    @OneToOne(cascade = CascadeType.ALL)
    private Valor valor;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Indice> indice = List.of(
            new Indice(null, 0L, Visualizacao.CALENDARIO),
            new Indice(null, 0L, Visualizacao.LISTA),
            new Indice(null, 0L, Visualizacao.TIMELINE),
            new Indice(null, 0L, Visualizacao.KANBAN));
}
