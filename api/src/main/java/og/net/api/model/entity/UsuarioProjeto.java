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
public class UsuarioProjeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "responsavel_id")
    private List<Usuario> responsaveis;

//    @Enumerated(EnumType.ORDINAL)
//    @JoinColumn(name = "permissao_id")
//    private Permissao permissao;

}
