package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.Mensagem;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatEquipeDTO {
    private List<Mensagem> mensagens;
    private Equipe equipe;
    public ChatEquipeDTO(Equipe equipe) {
        this.equipe = equipe;
    }
}
