package og.net.api.model.entity.Notificacao;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Projeto;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoProjeto extends Notificacao{
    @ManyToOne
    private Projeto projeto;
}
