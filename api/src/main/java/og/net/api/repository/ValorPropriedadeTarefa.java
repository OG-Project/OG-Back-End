package og.net.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValorPropriedadeTarefa extends JpaRepository<ValorPropriedadeTarefa,Long> {
}
