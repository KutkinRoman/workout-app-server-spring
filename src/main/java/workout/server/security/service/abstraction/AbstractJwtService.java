package workout.server.security.service.abstraction;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import workout.server.security.entity.inter.CustomUserDetails;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
public abstract class AbstractJwtService {

    private String accessSecret;

    private Long accessExpirationSecond;

    private String refreshSecret;

    private Long refreshExpirationSecond;

    protected void create (String accessSecret, Long accessExpirationSecond, String refreshSecret, Long refreshExpirationSecond) {
        this.accessSecret = accessSecret;
        this.accessExpirationSecond = accessExpirationSecond;
        this.refreshSecret = refreshSecret;
        this.refreshExpirationSecond = refreshExpirationSecond;
    }

    public abstract String generateJwtAccessToken (CustomUserDetails userDetails);

    public abstract String generateJwtRefreshToken (CustomUserDetails userDetails, String tokenId);

    public Date generateCreationDate () {
        return new Date ();
    }

    public Date generateExpirationDateAccessToken () {
        return this.generateExpirationDateBySeconds (accessExpirationSecond);
    }

    public Date generateExpirationDateRefreshToken () {
        return this.generateExpirationDateBySeconds (refreshExpirationSecond);
    }

    public Date generateExpirationDateBySeconds (Long seconds) {
        return new Date ((new Date ()).getTime () + (seconds * 1000));
    }

    public SecretKey getSecretKeyAccessToken () {
        return getSecretKeyByToken (accessSecret);
    }

    public SecretKey getSecretKeyRefreshToken () {
        return getSecretKeyByToken (refreshSecret);
    }

    public SecretKey getSecretKeyByToken (String token) {
        return Keys.hmacShaKeyFor (token.getBytes ());
    }

    public String getUserNameFromJwtAccessToken (String accessToken) {
        return getClaimsFromToken (accessToken, accessSecret).get ("username").toString ();
    }

    public String getUserNameFromJwtRefreshToken (String refreshToken) {
        return getClaimsFromToken (refreshToken, refreshSecret).getSubject ();
    }

    public String getIdFromJwtRefreshToken (String refreshToken) {
        return getClaimsFromToken (refreshToken, refreshSecret).getId ();
    }

    public boolean validateJwtAccessToken (String accessToken) {
        return validateJwtToken (accessToken, accessSecret);
    }

    public boolean validateJwtRefreshToken (String refreshToken) {
        return validateJwtToken (refreshToken, refreshSecret);
    }

    public boolean validateJwtToken (String token, String secret) {
        try {
            return getClaimsFromToken (token, secret).getExpiration ().after (new Date ());
        } catch (SignatureException e) {
            log.error ("Invalid JWT signature: {}", e.getMessage ());
        } catch (MalformedJwtException e) {
            log.error ("Invalid JWT token: {}", e.getMessage ());
        } catch (ExpiredJwtException e) {
            log.error ("JWT token is expired: {}", e.getMessage ());
        } catch (UnsupportedJwtException e) {
            log.error ("JWT token is unsupported: {}", e.getMessage ());
        } catch (IllegalArgumentException e) {
            log.error ("JWT claims string is empty: {}", e.getMessage ());
        }
        return false;
    }

    public Claims getClaimsFromByAccessToken (String token) {
        return getClaimsFromToken (token, accessSecret);
    }

    public Claims getClaimsFromToken (String token, String secret) {
        String key = Base64.getEncoder ().encodeToString (secret.getBytes ());
        return Jwts.parserBuilder ()
                .setSigningKey (key)
                .build ()
                .parseClaimsJws (token)
                .getBody ();
    }

}
