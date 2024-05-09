package og.net.api.controller;

import og.net.api.service.ExceptionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ExceptionLogService exceptionLogService;

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        // Log the exception
        exceptionLogService.logException(e.getMessage());
    }
}
