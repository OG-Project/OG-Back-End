package og.net.api.repository;

import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {

   List<Projeto> findByNome(String nome);
}
