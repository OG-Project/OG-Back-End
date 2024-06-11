package og.net.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import og.net.api.service.ExceptionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teste-kafka")
public class GlobalExceptionHandler {

    @Autowired
    private ExceptionLogService exceptionLogService;

    @PostMapping
    @ExceptionHandler(Exception.class)
    public void handleException(@RequestBody String e) throws JsonProcessingException {
        // Log the exception
        exceptionLogService.logException(e);
    }
}
