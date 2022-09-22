package workout.server;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import workout.server.security.constans.RoleName;
import workout.server.security.entity.AppUserImpl;
import workout.server.security.entity.UserRole;
import workout.server.security.repository.RoleRepository;
import workout.server.security.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataBaseService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @PostConstruct
    private void init () {
        if (!roleRepository.existsByName (RoleName.ROLE_ADMIN)) {
            Arrays.stream (RoleName.values ()).forEach (this::initRoleByName);
            initAdmin ();
            initUser ();
        }
    }

    private void initRoleByName (RoleName name) {
        if (!roleRepository.existsByName (name)) {
            UserRole role = new UserRole ();
            role.setName (name);
            roleRepository.save (role);
        }
    }

    private void initAdmin () {
        String username = "admin";
        if (userRepository.findByUsername (username).isEmpty ()) {
            AppUserImpl admin = new AppUserImpl ();
            admin.setUsername (username);
            admin.setPassword (new BCryptPasswordEncoder ().encode (username));
            admin.setRoles (roleRepository.findAll ());
            admin.setCreated (LocalDateTime.now ());
            userRepository.save (admin);
        }
    }

    private void initUser () {
        String username = "user";
        if (userRepository.findByUsername (username).isEmpty ()) {
            AppUserImpl user = new AppUserImpl ();
            user.setUsername (username);
            user.setPassword (new BCryptPasswordEncoder ().encode (username));
            user.setRoles (List.of (roleRepository.getByName (RoleName.ROLE_USER)));
            user.setCreated (LocalDateTime.now ());
            userRepository.save (user);
        }
    }

}
