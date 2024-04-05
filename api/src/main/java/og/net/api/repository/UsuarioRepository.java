package og.net.api.repository;

import og.net.api.model.dto.UsuarioCadastroDTO;
import og.net.api.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByNome(String nome);

    List<Usuario> findByUsername(String username);

    List<Usuario> findByEmail(String email);

    Usuario findByEquipesContaining(EquipeUsuario equipeUsuario);
}
