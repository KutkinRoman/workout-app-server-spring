package workout.server.security.entity.inter;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {

    Long getId ();

}
