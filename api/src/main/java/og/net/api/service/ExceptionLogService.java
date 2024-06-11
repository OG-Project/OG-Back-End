package og.net.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import og.net.api.configuration.ExceptionErrorProducer;
import og.net.api.model.entity.ExceptionLog.ExceptionLog;
import og.net.api.repository.ExceptionLogRepository.ExceptionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExceptionLogService {

    @Autowired
    private ExceptionErrorProducer exceptionErrorProducer;

    @Autowired
    private ExceptionLogRepository exceptionLogRepository;

    public void logException(String exceptionMensagem) throws JsonProcessingException {
        exceptionErrorProducer.sendMessage("vai se fuder");
        ExceptionLog exceptionLog = new ExceptionLog();
        exceptionLog.setExceptionMensagem(exceptionMensagem);
        exceptionLog.setDataError(LocalDateTime.now());
        exceptionLogRepository.save(exceptionLog);
    }

}
