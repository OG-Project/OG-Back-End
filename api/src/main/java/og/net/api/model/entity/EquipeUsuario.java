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
public class EquipeUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
<<<<<<< HEAD
    @ManyToOne(cascade = CascadeType.PERSIST)
=======
    @ManyToOne(cascade = CascadeType.ALL)
>>>>>>> d6219eea0907cee5863923da93475bae644c725e
    @JoinColumn(name="equipe_id")
    private Equipe equipe;
    @Enumerated(EnumType.ORDINAL)
    @JoinColumn(name = "permissao_id")
    private Permissao permissao;

}
