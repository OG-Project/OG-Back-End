package og.net.api.model.entity.Historico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.Tarefa;
import og.net.api.model.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String mensagem;
    @ManyToOne
    private Usuario criador;
    @OneToOne
    private Projeto projeto;
    @OneToOne
    private Tarefa tarefa;
    private LocalDateTime dataDeEnvio = LocalDateTime.now();
}
