package og.net.api.repository;

import og.net.api.model.entity.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {
}
