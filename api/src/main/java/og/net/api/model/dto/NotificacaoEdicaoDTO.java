package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import og.net.api.model.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class NotificacaoEdicaoDTO implements IDTO{
    private Integer id;
    private String mensagem;
    private Usuario criador;
    private List<Usuario> Receptores;
    private LocalDateTime dataDeEnvio;
}
