package workout.server.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workout.server.security.entity.AppUserImpl;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUserImpl, Long> {

    AppUserImpl getByUsername (String username);

    Optional<AppUserImpl> findByUsername (String username);
}
