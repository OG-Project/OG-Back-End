package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private Boolean ativo;
    private LocalDate dataCriacao;
    private String cor;
    @JoinColumn(name = "tarefa_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<ValorPropriedadeTarefa> valorPropriedadeTarefas;
    @ManyToOne
    private Status status;
    @PrePersist
    private void inserirData(){
        this.dataCriacao= LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }
}
