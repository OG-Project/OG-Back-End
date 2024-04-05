package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioProjeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Usuario responsavel;



    //    @Enumerated(EnumType.ORDINAL)
//    @JoinColumn(name = "permissao_id")
//    private Permissao permissao;



}
