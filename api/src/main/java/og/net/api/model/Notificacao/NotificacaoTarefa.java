package og.net.api.model.Notificacao;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.Tarefa;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoTarefa extends Notificacao{
    @OneToOne
    private Tarefa tarefa;
}
