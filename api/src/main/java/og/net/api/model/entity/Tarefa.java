package og.net.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDateTime dataCriacao;
    private String cor;
    @JoinColumn(name = "tarefa_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<ValorPropriedadeTarefa> valorPropriedadeTarefas;
    @ManyToOne
    private Status status;
    @OneToMany(cascade = CascadeType.ALL)
    private List<SubTarefa> subTarefas;
    @OneToMany
    private List<Comentario> comentarios;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Arquivo> arquivos;
}
