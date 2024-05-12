package og.net.api.repository.NotificacaoRepositorys;

import og.net.api.model.entity.Notificacao.NotificacaoProjeto;
import og.net.api.model.entity.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacaoProjetoRepository  extends JpaRepository<NotificacaoProjeto, Integer> {

    void deleteNotificacaoProjetosByProjeto(Projeto projeto);
}
