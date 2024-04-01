package og.net.api.repository;

import og.net.api.model.entity.Propriedade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PropriedadeRepository extends JpaRepository<Propriedade, Integer> {


}
