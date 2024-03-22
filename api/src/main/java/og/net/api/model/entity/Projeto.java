package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projeto")

public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    @Column(updatable = false)
    private LocalDateTime dataCriacao;
    @OneToMany
    private List<Tarefa>  tarefas;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Propriedade> propriedades;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id")
    private List<Status> statusList;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "projeto_id")
    private List<ProjetoEquipe> projetoEquipes;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private List<UsuarioProjeto>  responsveis;

}
