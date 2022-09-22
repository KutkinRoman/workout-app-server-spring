package workout.server.app.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import workout.server.app.exception.SaveNotAvailableException;
import workout.server.app.payload.response.ErrorResponse;

public class AuthExceptionHandler {

//    @ExceptionHandler(NotAuthorizedUserException.class)
//    public ResponseEntity<?> notAuthorizedUserExceptionHandler () {
//        return ResponseEntity.status (HttpStatus.UNAUTHORIZED).build ();
//    }
//
//    @ExceptionHandler(ForbiddenUserException.class)
//    public ResponseEntity<?> forbiddenUserExceptionHandler () {
//        return ResponseEntity.status (HttpStatus.FORBIDDEN).build ();
//    }

    @ExceptionHandler(SaveNotAvailableException.class)
    public ResponseEntity<?> forbiddenUserExceptionHandler (SaveNotAvailableException e) {
        ErrorResponse body = new ErrorResponse (e.getMessage ());
        return ResponseEntity
                .status (HttpStatus.FORBIDDEN)
                .body (body);
    }
}
