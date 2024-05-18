package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Mensagem;
import og.net.api.model.entity.Usuario;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatPessoalDTO {
    private List<Mensagem> mensagens;
    private List<Usuario> usuarios;
}
