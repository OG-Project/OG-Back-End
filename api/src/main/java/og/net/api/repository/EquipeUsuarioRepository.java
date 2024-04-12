package og.net.api.repository;

import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.EquipeUsuario;
import og.net.api.model.entity.ProjetoEquipe;
import og.net.api.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipeUsuarioRepository extends JpaRepository<EquipeUsuario,Integer> {

    List<EquipeUsuario> findAllByEquipe_id(Integer id);

    List<EquipeUsuario> findAllByEquipe(Equipe equipe);

    EquipeUsuario findEquipeUsuarioByCriador(Boolean isCriador);
}
