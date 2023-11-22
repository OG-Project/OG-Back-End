package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusProjetoTarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany
    @JoinColumn(name = "status_id")
    private List<Status> status;
    @ManyToOne
    @JoinColumn(name = "tarefa_id")
    private Tarefa tarefa;
    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;
}
