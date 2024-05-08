package og.net.api.repository;

import og.net.api.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByNome(String nome);

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByEmail(String email);

    Usuario findByEquipesContaining(EquipeUsuario equipeUsuario);



}
