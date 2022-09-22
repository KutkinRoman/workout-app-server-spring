package workout.server.security.entity.inter;

import org.springframework.security.core.GrantedAuthority;
import workout.server.security.constans.RoleName;
import workout.server.security.entity.UserRole;

import java.util.Collection;

public interface AppUser extends CustomUserDetails {

    Long getId ();

    void setId (Long id);

    String getUsername ();

    void setUsername (String username);

    String getPassword ();

    void setPassword (String password);

    Collection<UserRole> getRoles ();

    void setRoles (Collection<UserRole> roles);

    void setAuthorities (Collection<? extends GrantedAuthority> authorities);

    boolean containsRole (RoleName roleName);
}
