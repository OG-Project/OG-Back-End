package og.net.api.repository.NotificacaoRepositorys;

import og.net.api.model.entity.Notificacao.NotificacaoTarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacaoTarefaRepository  extends JpaRepository<NotificacaoTarefa, Integer> {
}
