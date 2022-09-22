package workout.server.app.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicationInfo {

    private final String applicationName;

    private final LocalDateTime timestamp;

    public ApplicationInfo (String applicationName) {
        this.applicationName = applicationName;
        this.timestamp = LocalDateTime.now ();
    }
}
