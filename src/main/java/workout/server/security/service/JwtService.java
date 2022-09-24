package workout.server.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import workout.server.security.entity.AppUserImpl;
import workout.server.security.entity.inter.CustomUserDetails;
import workout.server.security.service.abstraction.AbstractJwtService;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtService extends AbstractJwtService {

    @Value("${jwt.access.secret}")
    private String accessSecret;

    @Value("${jwt.access.expiration.second}")
    private Long accessExpirationSecond;

    @Value("${jwt.refresh.secret}")
    private String refreshSecret;

    @Value("${jwt.refresh.expiration.second}")
    private Long refreshExpirationSecond;

    @PostConstruct
    protected void create () {
        super.create (accessSecret, accessExpirationSecond, refreshSecret, refreshExpirationSecond);
    }

    @Override
    public String generateJwtAccessToken (CustomUserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<> ();
        claims.put ("username", userDetails.getUsername ());
        claims.put ("roles",
                userDetails.getAuthorities ()
                        .stream ()
                        .map (Objects::toString)
                        .collect (Collectors.toList ())
        );
        return Jwts
                .builder ()
                .setSubject (userDetails.getId ().toString ())
                .addClaims (claims)
                .setIssuedAt (generateCreationDate ())
                .setExpiration (generateExpirationDateAccessToken ())
                .signWith (getSecretKeyAccessToken ())
                .compact ();
    }

    @Override
    public String generateJwtRefreshToken (CustomUserDetails userDetails, String tokenId) {
        return Jwts
                .builder ()
                .setId (tokenId)
                .setSubject (userDetails.getId ().toString ())
                .setIssuedAt (generateCreationDate ())
                .setExpiration (generateExpirationDateRefreshToken ())
                .signWith (getSecretKeyAccessToken ())
                .compact ();
    }

    @Override
    public CustomUserDetails parseUserDetails (String accessToken) {
        final Claims claims = getClaimsFromByAccessToken (accessToken);
        Long userId = Long.parseLong (claims.getSubject ());
        String username = ( String ) claims.get ("username");
        List<String> roleNames = ( List<String> ) claims.get ("roles");
        Collection<SimpleGrantedAuthority> authorities = roleNames
                .stream ()
                .map (SimpleGrantedAuthority::new)
                .collect (Collectors.toList ());
        return new AppUserImpl (userId, username, authorities);
    }


}
