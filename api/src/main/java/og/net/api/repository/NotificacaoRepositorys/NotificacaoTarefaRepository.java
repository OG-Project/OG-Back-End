package og.net.api.repository.NotificacaoRepositorys;

import og.net.api.model.Notificacao.Notificacao;
import og.net.api.model.Notificacao.NotificacaoTarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacaoTarefaRepository  extends JpaRepository<NotificacaoTarefa, Integer> {
}
