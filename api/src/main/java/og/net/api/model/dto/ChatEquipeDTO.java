package og.net.api.model.dto;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.Mensagem;
import og.net.api.model.entity.Usuario;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatEquipeDTO {
    private List<Mensagem> mensagens;
    private Equipe equipe;
}
