package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.model.entity.ExceptionLog.ExceptionLog;
import og.net.api.repository.ExceptionLogRepository.ExceptionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExceptionLogService {

    @Autowired
    private ExceptionLogRepository exceptionLogRepository;

    public void logException(String exceptionMensagem){
        ExceptionLog exceptionLog = new ExceptionLog();
        exceptionLog.setExceptionMensagem(exceptionMensagem);
        exceptionLog.setDataError(LocalDateTime.now());
        exceptionLogRepository.save(exceptionLog);
    }

}
