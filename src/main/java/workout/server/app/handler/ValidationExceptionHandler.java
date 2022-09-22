package workout.server.app.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ValidationExceptionHandler extends AuthExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler (BindingResult bindingResult) {
        return ResponseEntity
                .badRequest ()
                .body (bindingResult.getModel ());
    }

}
