package workout.server.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workout.server.security.entity.RefreshTokenStore;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenStore, String> {

    Optional<RefreshTokenStore> findByUsername (String username);

    void deleteByUsername (String username);
}
