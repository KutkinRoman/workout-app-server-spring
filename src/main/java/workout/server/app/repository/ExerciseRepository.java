package workout.server.app.repository;

import org.springframework.stereotype.Repository;
import workout.server.app.entity.ExerciseEntity;
import workout.server.app.entity.ExerciseGroup;
import workout.server.app.entity.constant.AccessType;
import workout.server.app.entity.constant.EntityStatus;
import workout.server.app.repository.inter.BaseEntityRepository;
import workout.server.security.entity.AppUserImpl;

import java.util.List;

@Repository
public interface ExerciseRepository extends BaseEntityRepository<ExerciseEntity> {

    List<ExerciseEntity> findAllByGroupAndUserAndStatusOrGroupAndAccessAndStatus (
            ExerciseGroup v1,
            AppUserImpl v2,
            EntityStatus v3,
            ExerciseGroup v4,
            AccessType v5,
            EntityStatus v6
    );
}
