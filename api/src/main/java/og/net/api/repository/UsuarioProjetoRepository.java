package og.net.api.repository;

import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.EquipeUsuario;
import og.net.api.model.entity.Usuario;
import og.net.api.model.entity.UsuarioProjeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioProjetoRepository extends JpaRepository<UsuarioProjeto,Integer> {

    void deleteByIdResponsavel(Integer id);

}
