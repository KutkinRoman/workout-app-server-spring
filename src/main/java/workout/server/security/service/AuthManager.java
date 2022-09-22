package workout.server.security.service;

import workout.server.security.entity.AppUserImpl;
import workout.server.security.payload.response.AuthResponse;

public interface AuthManager {

    AuthResponse login (String username, String password);

    AuthResponse refresh (String refreshToken);

    void deleteRefreshTokenByUser (AppUserImpl user);

}
