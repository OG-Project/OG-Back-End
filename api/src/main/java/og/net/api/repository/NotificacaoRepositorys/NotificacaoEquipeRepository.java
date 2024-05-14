package og.net.api.repository.NotificacaoRepositorys;

import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.Notificacao.NotificacaoEquipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoEquipeRepository  extends JpaRepository<NotificacaoEquipe, Integer> {

    List<NotificacaoEquipe> findAllByEquipe(Equipe equipe);
}
