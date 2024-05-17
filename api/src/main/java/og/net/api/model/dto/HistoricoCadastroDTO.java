package og.net.api.model.dto;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.Tarefa;
import og.net.api.model.entity.Usuario;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class HistoricoCadastroDTO implements IDTO{
    private String mensagem;
    private Usuario criador;
    private Projeto projeto;
    private Tarefa tarefa;
    private LocalDateTime dataDeEnvio;
}
