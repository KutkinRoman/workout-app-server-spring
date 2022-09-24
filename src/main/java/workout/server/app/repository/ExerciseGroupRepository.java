package workout.server.app.repository;

import org.springframework.stereotype.Repository;
import workout.server.app.entity.ExerciseCategory;
import workout.server.app.entity.ExerciseGroup;
import workout.server.app.entity.constant.AccessType;
import workout.server.app.entity.constant.EntityStatus;
import workout.server.app.repository.inter.BaseEntityRepository;
import workout.server.security.entity.AppUserImpl;

import java.util.List;


@Repository
public interface ExerciseGroupRepository extends BaseEntityRepository<ExerciseGroup> {

    List<ExerciseGroup> findAllByCategoryAndUserAndStatusOrCategoryAndAccessAndStatus (
            ExerciseCategory v1,
            AppUserImpl v2,
            EntityStatus v3,
            ExerciseCategory v4,
            AccessType v5,
            EntityStatus v6
    );


}
