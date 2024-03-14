package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValorPropriedadeTarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Propriedade propriedade;
    @OneToOne(cascade = CascadeType.ALL)
    private Valor valor;
    private Boolean visivel;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Indice> indice;

}
