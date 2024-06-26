package og.net.api.repository;

import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.ProjetoEquipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoEquipeRepository extends JpaRepository<ProjetoEquipe, Integer> {

    List<ProjetoEquipe> findAllByEquipe_Id(Integer id);

    List<ProjetoEquipe> findAllByEquipe(Equipe equipe);
}
