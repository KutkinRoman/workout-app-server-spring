package workout.server.app.service.abstraction;

import workout.server.app.entity.inter.BaseEntity;
import workout.server.app.repository.inter.BaseEntityRepository;
import workout.server.security.entity.AppUserImpl;
import workout.server.security.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static workout.server.app.entity.constant.AccessType.PUBLIC;

public abstract class AbstractSynchronizationService<T extends BaseEntity> {

    protected abstract BaseEntityRepository<T> getRepository ();

    public List<T> getSyncEntities (LocalDateTime latestDate) {

        AppUserImpl user = UserServiceImpl.getCurrentAuthUser ();
        if (Objects.isNull (latestDate)){
            return getRepository ()
                    .findByUserOrAccess
                            (user, PUBLIC);
        }
        return getRepository ()
                .findByUserAndUpdatedAfterOrAccessAndUpdatedAfter
                        (user, latestDate, PUBLIC, latestDate);
    }
}
