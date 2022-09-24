package workout.server.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import workout.server.app.entity.ExerciseCategory;
import workout.server.app.entity.ExerciseGroup;
import workout.server.app.repository.ExerciseGroupRepository;
import workout.server.app.repository.inter.BaseEntityRepository;
import workout.server.app.service.abstraction.AbstractEntityService;
import workout.server.security.entity.AppUserImpl;
import workout.server.security.service.UserServiceImpl;

import java.util.List;

import static workout.server.app.entity.constant.AccessType.PUBLIC;
import static workout.server.app.entity.constant.EntityStatus.ACTIVE;

@Service
@RequiredArgsConstructor
public class ExerciseGroupService extends AbstractEntityService<ExerciseGroup> {

    private final ExerciseCategoryService categoryService;
    private final ExerciseGroupRepository groupRepository;

    public List<ExerciseGroup> getAllByCategoryId (String categoryId) {
        ExerciseCategory category = categoryService.getById (categoryId);
        AppUserImpl user = UserServiceImpl.getCurrentAuthUser ();
        return groupRepository
                .findAllByCategoryAndUserAndStatusOrCategoryAndAccessAndStatus
                        (category, user, ACTIVE, category, PUBLIC, ACTIVE);
    }

    @Override
    protected BaseEntityRepository<ExerciseGroup> getRepository () {
        return groupRepository;
    }


}
