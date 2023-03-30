package my.own.adp.data.handler;

import lombok.extern.slf4j.Slf4j;
import my.own.adp.data.handler.exception.IncomingMsgJournalNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("Произошла ошибка валидации данных {}", ex.getMessage());
        return ResponseEntity.badRequest().body("Неверные параметры запроса");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleConstraintViolationException(MethodArgumentNotValidException ex) {
        log.error("Произошла ошибка валидации данных {}", ex.getMessage());
        return ResponseEntity.badRequest().body("Неверные параметры запроса");
    }

    @ExceptionHandler(IncomingMsgJournalNotFoundException.class)
    public ResponseEntity<Object> handleIncomingMsgJournalNotFoundException(IncomingMsgJournalNotFoundException ex) {
        log.error("Произошла ошибка {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
