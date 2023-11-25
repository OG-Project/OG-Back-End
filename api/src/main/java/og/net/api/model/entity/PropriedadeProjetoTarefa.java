package og.net.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropriedadeProjetoTarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;
    @OneToOne(mappedBy = "valor")
    @JoinColumn(name = "propriedade_id")
    @JsonIgnore
    private Propriedade propriedade;
    private String valor;
}
