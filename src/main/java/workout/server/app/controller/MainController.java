package workout.server.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import workout.server.app.payload.response.ApplicationInfo;

@RestController
public class MainController {

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/")
    public ResponseEntity<ApplicationInfo> index () {
        final ApplicationInfo info = new ApplicationInfo (applicationName);
        return ResponseEntity.ok (info);
    }


}
