package nl.vtek.names.game.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.InputMismatchException;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(InputMismatchException.class)
    public ResponseEntity<String> handleInputMismatchException(InputMismatchException exception) {
        String message = "Hint moet één woord zijn en mag geen cijfers bevatten";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }
}
