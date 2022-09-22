package workout.server.security.entity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class AppAuthentication extends UsernamePasswordAuthenticationToken {

    private final AppUserImpl authUser;

    public AppAuthentication (UserDetails userDetails, AppUserImpl user) {
        super (userDetails, null, userDetails.getAuthorities ());
        this.authUser = user;
    }

    public AppUserImpl getAuthUser () {
        return authUser;
    }
}
