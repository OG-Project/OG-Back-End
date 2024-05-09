package og.net.api.repository.ExceptionLogRepository;

import og.net.api.model.entity.ExceptionLog.ExceptionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionLogRepository extends JpaRepository<ExceptionLog, Integer> {
}
