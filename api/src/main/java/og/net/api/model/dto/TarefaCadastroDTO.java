package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.Status;
import og.net.api.model.entity.ValorPropriedadeTarefa;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaCadastroDTO implements IDTO{

    private String nome;
    private String descricao;
    private Boolean ativo;
    private LocalDateTime data_criacao;
    private String cor;
    private List<ValorPropriedadeTarefa> valorPropriedadeTarefas;
    private Status status;
}
