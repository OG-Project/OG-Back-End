package og.net.api.repository;

import og.net.api.model.entity.Convite;
import og.net.api.model.entity.ConviteParaEquipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConviteParaEquipeRepository extends JpaRepository<Convite,Integer> {
    List<ConviteParaEquipe>findConvitesByAceito(Boolean aceito);
}
