package og.net.api.repository.NotificacaoRepositorys;

import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.Notificacao.NotificacaoConvite;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoConviteRepository  extends JpaRepository<NotificacaoConvite, Integer> {

    List<NotificacaoConvite> findNotificacaoConviteByConviteParaEquipe_Equipe(Equipe equipe);

    List<NotificacaoConvite> findNotificacaoConviteByConviteParaProjeto_Projeto(Projeto projeto);



}
