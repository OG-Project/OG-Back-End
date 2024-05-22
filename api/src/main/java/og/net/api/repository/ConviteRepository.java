package og.net.api.repository;

import og.net.api.model.entity.Convite;
import og.net.api.model.entity.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConviteRepository extends JpaRepository<Convite,Integer> {

}
