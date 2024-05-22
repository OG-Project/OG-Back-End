package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "projeto")

public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    @Column(updatable = false)
    private LocalDateTime dataCriacao;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Tarefa>  tarefas;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Propriedade> propriedades;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private List<Status> statusList;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "projeto_id")
    private List<ProjetoEquipe> projetoEquipes;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "projeto_id")
    private List<UsuarioProjeto>  responsaveis;
    private LocalTime tempoAtuacao;
    private LocalDate dataFinal;
    private String categoria;
    private Integer indexLista = 1;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comentario> comentarios;

    public Projeto() {
        this.categoria = "meus-projetos";
        this.indexLista = +1;
        this.dataCriacao = LocalDateTime.now();
    }

    public Projeto(List<Status> statusList) {
        this.statusList = statusList;
        this.tarefas = new ArrayList<>();
        this.dataCriacao = LocalDateTime.now();
    }
}
