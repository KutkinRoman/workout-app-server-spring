package workout.server.security.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import workout.server.security.constans.TokenType;
import workout.server.security.entity.inter.CustomUserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filter) throws ServletException, IOException {
        try {
            Optional<String> jwt = parseJwtFromHeader (request);
            if (jwt.isPresent () && jwtService.validateJwtAccessToken (jwt.get ())) {
                authenticate (request, jwt.get ());
            }
        } catch (Exception e) {
            logger.error ("Cannot set user authentication: {}", e);
        }
        filter.doFilter (request, response);
    }

    private Optional<String> parseJwtFromHeader (HttpServletRequest request) {
        String headerAuth = request.getHeader (HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText (headerAuth) && headerAuth.startsWith (TokenType.BEARER.getHeader ())) {
            String token = headerAuth.substring (TokenType.BEARER.getHeader ().length (), headerAuth.length ());
            return Optional.of (token);
        }
        return Optional.empty ();
    }

    public void authenticate (HttpServletRequest request, String accessToken) {
        CustomUserDetails user = jwtService.parseUserDetails (accessToken);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken (user, null, user.getAuthorities ());
        authentication.setDetails (new WebAuthenticationDetailsSource ().buildDetails (request));
        SecurityContextHolder.getContext ().setAuthentication (authentication);
    }


}
