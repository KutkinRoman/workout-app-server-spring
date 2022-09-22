package workout.server.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workout.server.app.handler.AppExceptionHandler;
import workout.server.security.entity.AppAuthentication;
import workout.server.security.payload.request.LoginForm;
import workout.server.security.payload.request.RefreshRequest;
import workout.server.security.payload.response.AuthResponse;
import workout.server.security.service.AuthManager;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends AppExceptionHandler {

    private final AuthManager authManager;

    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody LoginForm form) {
        AuthResponse authResponse = authManager.login (form.getUsername (), form.getPassword ());
        return ResponseEntity.ok (authResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh (@RequestBody RefreshRequest request) {
        AuthResponse authResponse = authManager.refresh (request.getRefreshToken ());
        return ResponseEntity.ok (authResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/logout")
    public void logout (AppAuthentication authentication) {
        authManager.deleteRefreshTokenByUser (authentication.getAuthUser ());
    }

}
