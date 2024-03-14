package og.net.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "propriedade")
public class Propriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @Enumerated(value = EnumType.STRING)
    private Tipo tipo;
    @JsonIgnore
    @OneToMany(mappedBy = "propriedade")
    private List<ValorPropriedadeTarefa> valorPropriedadeTarefas;
}
