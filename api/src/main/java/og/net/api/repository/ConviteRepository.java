package og.net.api.repository;

import og.net.api.model.entity.Convite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConviteRepository extends JpaRepository<Convite,Integer> {
}
