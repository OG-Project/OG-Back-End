package og.net.api.repository;

import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.ProjetoEquipe;
import og.net.api.model.entity.Propriedade;
import og.net.api.model.entity.Status;
import og.net.api.model.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {

   List<Projeto> findByNome(String nome);
   List<Projeto> findByEquipes_Equipe_Id(Integer id);

}
