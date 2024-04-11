package og.net.api.repository.NotificacaoRepositorys;

import og.net.api.model.entity.Notificacao.NotificacaoEquipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacaoEquipeRepository  extends JpaRepository<NotificacaoEquipe, Integer> {
}
