package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.PropriedadeProjetoTarefa;
import og.net.api.model.entity.Status;


import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TarefaEdicaoDTO implements IDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private Boolean ativo;
    private Date dataCriacao;
    private String cor;
    private List<Status> status;
    private Projeto projeto;;
}
