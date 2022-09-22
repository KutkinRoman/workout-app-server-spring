package workout.server.app.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import workout.server.app.payload.response.ErrorResponse;

@Slf4j
public class AppExceptionHandler extends ValidationExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> exceptionHandle (Exception e) {
//        try {
//            ErrorResponse body = new ErrorResponse (e.getMessage ());
//            return ResponseEntity
//                    .badRequest ()
//                    .body (body);
//        } finally {
//            throw new RuntimeException (e);
//        }
//    }

}
