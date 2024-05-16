package og.net.api.repository;

import og.net.api.model.entity.UsuarioAceito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioAceitoRepository extends JpaRepository<UsuarioAceito,Integer> {

    void deleteAllByUsuario_Id(Integer id);

}
