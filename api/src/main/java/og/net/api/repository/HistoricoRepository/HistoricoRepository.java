package og.net.api.repository.HistoricoRepository;

import og.net.api.model.entity.Historico.Historico;
import og.net.api.model.entity.Notificacao.Notificacao;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.Tarefa;
import og.net.api.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Integer> {
    List<Historico> findHistoricoByTarefa(Tarefa tarefa);
    List<Historico> findHistoricoByProjeto(Projeto projeto);
}
