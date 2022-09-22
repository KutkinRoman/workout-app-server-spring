package workout.server.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.server.security.entity.AppUserImpl;
import workout.server.security.entity.inter.CustomUserDetails;
import workout.server.security.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public CustomUserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername (username)
                .orElseThrow (() -> new UsernameNotFoundException ("User not found by username: " + username));
    }

    public static AppUserImpl getCurrentAuthUser () {
        return ( AppUserImpl ) SecurityContextHolder
                .getContext ()
                .getAuthentication ()
                .getPrincipal ();
    }

}
