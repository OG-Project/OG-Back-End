package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.Tarefa;
import og.net.api.model.entity.Usuario;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class HistoricoEdicaoDTO implements IDTO{
    private Integer id;
    private String mensagem;
    private Usuario criador;
    private Projeto projeto;
    private Tarefa tarefa;
    private LocalDateTime dataDeEnvio;
}
