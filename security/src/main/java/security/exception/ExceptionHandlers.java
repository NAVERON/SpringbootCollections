package security.exception;

import common.models.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlers {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CommonResponse<Object>> baseExceptionHandler(Exception exception) {

        return ResponseEntity.of(null);
    }

    @ExceptionHandler(value = SecurityBusinessException.class)
    public ResponseEntity<CommonResponse<Object>> businessExceptionHandler(SecurityBusinessException exception) {

        return ResponseEntity.ok(null);
    }
}