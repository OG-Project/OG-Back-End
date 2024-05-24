package og.net.api.model.entity.Notificacao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String mensagem;
    @ManyToOne
    private Usuario criador;
    @ManyToMany
    private List<Usuario> receptores;
    private LocalDateTime dataDeEnvio = LocalDateTime.now();
    private Boolean visto = false;
}
