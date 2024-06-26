package og.net.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tarefa")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private String cor;
    @JoinColumn(name = "tarefa_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<ValorPropriedadeTarefa> valorPropriedadeTarefas;
    @ManyToOne
    private Status status;
    @OneToMany(cascade = CascadeType.ALL)
    private List<SubTarefa> subTarefas;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comentario> comentarios;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Arquivo> arquivos = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Indice> indice;
    @OneToMany(cascade = CascadeType.ALL)
    private List<UsuarioTarefa> responsaveis;
    private LocalTime tempoAtuacao;


    public Tarefa(String nome, String descricao, String cor,  Status status,List<Indice> indice) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataCriacao = LocalDateTime.now();
        this.cor = cor;
        this.valorPropriedadeTarefas = null;
        this.status = status;
        this.subTarefas = null;
        this.comentarios = null;
        this.arquivos = new ArrayList<>();
        this.indice = indice;
    }
}
