package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.dto.IDTO;
import og.net.api.model.dto.ProjetoCadastroDTO;
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
    private List<ProjetoEquipe> projetosEquipes;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Usuario> responsaveis;
}
