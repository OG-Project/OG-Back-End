package og.net.api.repository;

import og.net.api.model.entity.Usuario;
import og.net.api.model.entity.UsuarioDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDetailsEntityRepository extends JpaRepository<UsuarioDetailsEntity, Integer> {

    UsuarioDetailsEntity findByUsuario (Usuario usuario);
}
