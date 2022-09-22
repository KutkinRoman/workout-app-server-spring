package workout.server.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import workout.server.security.entity.inter.CustomUserDetails;

public interface UserService extends UserDetailsService {

    @Override
    CustomUserDetails loadUserByUsername (String username) throws UsernameNotFoundException;
}
