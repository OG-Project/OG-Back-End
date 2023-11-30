package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProjetoCadastroDTO implements IDTO{

    private String nome;
    private String descricao;
    private Date dataCriacao;
    private List<Status> statusList;
    private Set<Tarefa> tarefas;
    private List<Propriedade> propriedades;
}
