package og.net.api.model.entity.Notificacao;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Tarefa;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoTarefa extends Notificacao{
    @ManyToOne
    private Tarefa tarefa;
}
