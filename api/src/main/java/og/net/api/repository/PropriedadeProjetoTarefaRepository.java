package og.net.api.repository;

import og.net.api.model.entity.PropriedadeProjetoTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropriedadeProjetoTarefaRepository extends JpaRepository<PropriedadeProjetoTarefa,Integer> {
}
