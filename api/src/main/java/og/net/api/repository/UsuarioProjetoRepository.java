package og.net.api.repository;

import og.net.api.model.entity.UsuarioProjeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioProjetoRepository extends JpaRepository<UsuarioProjeto,Integer> {
}
