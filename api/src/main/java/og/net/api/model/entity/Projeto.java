package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.Format;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    private LocalDateTime dataCriacao;
    @OneToMany
    @JoinColumn(name = "projeto_id")
    private Set<Tarefa>  tarefas;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "projeto_id")
    private List<Propriedade> propriedades;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "status_id")
    private List<Status> statusList;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "projeto_id")
    private List<ProjetoEquipe> equipes;
    @PrePersist
    private void inserirData(){
        this.dataCriacao= LocalDateTime.now();
    }
}
