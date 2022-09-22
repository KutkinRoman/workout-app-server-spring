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

    List<ExerciseGroup> findAllByCategoryAndStatusAndUserOrAccess (ExerciseCategory category, EntityStatus status, AppUserImpl user, AccessType access);

}
