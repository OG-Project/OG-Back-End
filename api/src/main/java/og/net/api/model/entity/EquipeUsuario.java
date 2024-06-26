package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipeUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="equipe_id")
    private Equipe equipe;
    @Enumerated(EnumType.ORDINAL)
    @JoinColumn(name = "permissao_id")
    private List<Permissao> permissao;
    private Boolean criador = false;
}
