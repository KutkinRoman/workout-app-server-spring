package workout.server;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class WorkoutServerApplication {

    private final DataBaseService dataBaseService;

    public static void main (String[] args) {
        SpringApplication.run (WorkoutServerApplication.class, args);
    }

}
