package og.net.api.repository;

import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer> {

    List<Equipe> findByNome(String nome_equipe);
}
