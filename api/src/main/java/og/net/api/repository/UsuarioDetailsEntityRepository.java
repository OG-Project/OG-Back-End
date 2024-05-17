package og.net.api.repository;

import og.net.api.model.entity.Usuario;
import og.net.api.model.entity.UsuarioDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioDetailsEntityRepository extends JpaRepository<UsuarioDetailsEntity, Integer> {

    UsuarioDetailsEntity findByUsuario (Usuario usuario);

     Optional<UsuarioDetailsEntity> deleteByUsuario_Id(Integer id);
}
