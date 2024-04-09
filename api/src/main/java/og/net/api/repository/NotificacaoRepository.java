package og.net.api.repository;

import og.net.api.model.entity.Notificacao;
import og.net.api.model.entity.ProjetoEquipe;
import og.net.api.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {

    List<Notificacao> findNotificacaosByReceptores(List<Usuario> receptores);
}
