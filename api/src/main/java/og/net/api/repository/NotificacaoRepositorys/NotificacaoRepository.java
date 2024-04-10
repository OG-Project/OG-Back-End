package og.net.api.repository.NotificacaoRepositorys;

import og.net.api.model.Notificacao.Notificacao;
import og.net.api.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {

    List<Notificacao> findNotificacaosByReceptoresContaining(Usuario receptor);
}
