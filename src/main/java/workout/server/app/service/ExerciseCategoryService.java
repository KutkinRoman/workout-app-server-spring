package workout.server.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import workout.server.app.entity.ExerciseCategory;
import workout.server.app.repository.ExerciseCategoryRepository;
import workout.server.app.repository.inter.BaseEntityRepository;
import workout.server.app.service.abstraction.AbstractEntityService;

@Service
@RequiredArgsConstructor
public class ExerciseCategoryService extends AbstractEntityService<ExerciseCategory> {

    private final ExerciseCategoryRepository repository;

    @Override
    protected BaseEntityRepository<ExerciseCategory> getRepository () {
        return repository;
    }

}
