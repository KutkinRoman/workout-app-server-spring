package workout.server.app.repository;

import org.springframework.stereotype.Repository;
import workout.server.app.entity.ExerciseCategory;
import workout.server.app.repository.inter.BaseEntityRepository;


@Repository
public interface ExerciseCategoryRepository extends BaseEntityRepository<ExerciseCategory> {


}
