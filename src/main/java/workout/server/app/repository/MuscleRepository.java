package workout.server.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workout.server.app.entity.Muscle;

@Repository
public interface MuscleRepository extends JpaRepository<Muscle, Long> {

}
