package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Status;
import og.net.api.model.entity.Usuario;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TarefaEdicaoDTO implements IDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private Boolean ativo;
    private Date data_criacao;
    private Date data_termino;
    private List<Status> status;
}
