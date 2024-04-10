package og.net.api.model.Notificacao;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.Projeto;
import og.net.api.service.ProjetoService;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoProjeto extends Notificacao{
    @OneToOne
    private Projeto projeto;
}
