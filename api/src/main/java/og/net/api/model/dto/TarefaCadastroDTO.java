package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Status;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaCadastroDTO implements IDTO{

    private String nome;
    private String descricao;
    private Boolean ativo;
    private Date data_criacao;
    private String cor;
    private List<Status> status;
}
