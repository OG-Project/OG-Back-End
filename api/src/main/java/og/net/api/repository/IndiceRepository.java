package og.net.api.repository;

import og.net.api.model.entity.EquipeUsuario;
import og.net.api.model.entity.Indice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndiceRepository  extends JpaRepository<Indice,Integer> {
}
