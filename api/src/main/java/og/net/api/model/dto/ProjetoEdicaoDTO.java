package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProjetoEdicaoDTO implements IDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private List<Status> statusList;
    private List<Tarefa> tarefas;
    private List<Propriedade> propriedades;

    private List<ProjetoEquipe> projetoEquipes;

}
