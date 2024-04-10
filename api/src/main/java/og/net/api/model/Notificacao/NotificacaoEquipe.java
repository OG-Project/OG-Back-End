package og.net.api.model.Notificacao;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Equipe;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoEquipe extends Notificacao{
    @OneToOne
    private Equipe equipe;
}
