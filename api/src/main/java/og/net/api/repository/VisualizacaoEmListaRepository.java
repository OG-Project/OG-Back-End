package og.net.api.repository;

import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.VisualizacaoEmLista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisualizacaoEmListaRepository extends JpaRepository<VisualizacaoEmLista, Integer> {

    VisualizacaoEmLista findVisualizacaoEmListaByProjeto(Projeto projeto);
}
