package og.net.api.repository;

import og.net.api.model.entity.ValorPropriedadeTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValorPropriedadeTarefaRepository extends JpaRepository<ValorPropriedadeTarefa,Integer> {
}
