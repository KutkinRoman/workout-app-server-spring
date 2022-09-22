package workout.server.app.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {

    private final LocalDateTime timestamp;

    private final String message;

    public ErrorResponse (String message) {
        this.timestamp = LocalDateTime.now ();
        this.message = message;
    }
}
