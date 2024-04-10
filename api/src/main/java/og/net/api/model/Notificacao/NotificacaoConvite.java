package og.net.api.model.Notificacao;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Convite;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoConvite extends Notificacao{
    @OneToOne
    private Convite convite;
}
