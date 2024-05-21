package og.net.api.repository;

import og.net.api.model.entity.Chat;
import og.net.api.model.entity.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
}
