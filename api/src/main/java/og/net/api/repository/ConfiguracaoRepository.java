package og.net.api.repository;

import og.net.api.model.entity.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoRepository extends JpaRepository<Configuracao,Integer> {

}
