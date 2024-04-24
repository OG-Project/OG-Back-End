package og.net.api.repository;

import og.net.api.model.entity.Comentario;
import og.net.api.model.entity.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
}
