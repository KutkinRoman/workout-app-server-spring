package workout.server.security.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import workout.server.security.exception.ForbiddenUserException;
import workout.server.security.exception.NotValidUsernameOrPasswordException;
import workout.server.security.entity.AppUserImpl;
import workout.server.security.entity.RefreshTokenStore;
import workout.server.security.entity.inter.CustomUserDetails;
import workout.server.security.payload.response.AuthResponse;
import workout.server.security.repository.RefreshTokenRepository;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
class AuthManagerImpl implements AuthManager {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse login (String username, String password) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken (username, password);
            authenticationManager.authenticate (token);
            CustomUserDetails userDetails = userService.loadUserByUsername (username);
            return createAuthResponse (userDetails);
        } catch (BadCredentialsException e) {
            throw new NotValidUsernameOrPasswordException ();
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
    }

    @Transactional
    @Override
    public AuthResponse refresh (String refreshToken) {
        if (jwtService.validateJwtRefreshToken (refreshToken)) {
            String username = jwtService.getUserNameFromJwtRefreshToken (refreshToken);
            CustomUserDetails userDetails = userService.loadUserByUsername (username);
            return createAuthResponse (userDetails);
        }
        throw new ForbiddenUserException ();
    }

    @Transactional
    @Override
    public void deleteRefreshTokenByUser (AppUserImpl user) {
        refreshTokenRepository.deleteByUsername (user.getUsername ());
    }

    private AuthResponse createAuthResponse (CustomUserDetails userDetails) {
        String accessToken = jwtService.generateJwtAccessToken (userDetails);
        RefreshTokenStore tokenStore = createRefreshTokenStore (userDetails);
        String refreshToken = jwtService.generateJwtRefreshToken (userDetails, tokenStore.getTokenId ());
        return AuthResponse.builder ()
                .accessToken (accessToken)
                .refreshToken (refreshToken)
                .build ();
    }

    private RefreshTokenStore createRefreshTokenStore (CustomUserDetails userDetails) {
        RefreshTokenStore store = refreshTokenRepository.findByUsername (userDetails.getUsername ()).orElse (new RefreshTokenStore ());
        store.setUsername (userDetails.getUsername ());
        store.setTokenId (UUID.randomUUID ().toString ());
        return refreshTokenRepository.save (store);
    }

}
