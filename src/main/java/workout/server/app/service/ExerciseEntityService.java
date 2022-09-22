package workout.server.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import workout.server.app.entity.ExerciseEntity;
import workout.server.app.entity.ExerciseGroup;
import workout.server.app.repository.ExerciseRepository;
import workout.server.app.repository.inter.BaseEntityRepository;
import workout.server.app.service.abstraction.AbstractEntityService;
import workout.server.security.entity.AppUserImpl;
import workout.server.security.service.UserServiceImpl;

import java.util.List;

import static workout.server.app.entity.constant.AccessType.PUBLIC;
import static workout.server.app.entity.constant.EntityStatus.ACTIVE;

@Service
@RequiredArgsConstructor
public class ExerciseEntityService extends AbstractEntityService<ExerciseEntity> {

    private final ExerciseGroupService groupService;
    private final ExerciseRepository repository;

    public List<ExerciseEntity> getAllByGroupId (String groupId) {
        ExerciseGroup group = groupService.getById (groupId);
        AppUserImpl user = UserServiceImpl.getCurrentAuthUser ();
        return repository
                .findAllByGroupAndStatusAndUserOrAccess (group, ACTIVE, user, PUBLIC);
    }

    @Override
    protected BaseEntityRepository<ExerciseEntity> getRepository () {
        return repository;
    }
}
