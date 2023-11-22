package og.net.api.repository;

import og.net.api.model.entity.EquipeUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipeUsuarioRepository extends JpaRepository<EquipeUsuario,Integer> {
}
