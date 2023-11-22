package og.net.api.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import og.net.api.model.entity.UsuarioTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioTarefaRepository extends JpaRepository<UsuarioTarefa, Integer> {
}
