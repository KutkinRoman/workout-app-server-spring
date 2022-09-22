package workout.server.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workout.server.security.constans.RoleName;
import workout.server.security.entity.UserRole;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {

    UserRole getByName(RoleName name);

    boolean existsByName(RoleName name);
}
