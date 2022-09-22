package workout.server.app.service.abstraction;

import workout.server.app.entity.constant.AccessType;
import workout.server.app.entity.constant.EntityStatus;
import workout.server.app.entity.inter.BaseEntity;
import workout.server.app.exception.SaveNotAvailableException;
import workout.server.security.entity.AppUserImpl;
import workout.server.security.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractSaveService<T extends BaseEntity> extends AbstractRemovalService<T> {

    public T saveByCurrentAuthUser (T entity) {
        this.verifyByEntity (entity);
        return getRepository ().save (entity);
    }

    public Collection<T> saveAllCurrentAuthUser (Collection<T> entities) {
        entities.forEach (this::verifyByEntity);
        return getRepository ().saveAll (entities);
    }

    private void verifyByEntity (T entity) {
        AppUserImpl curUser = UserServiceImpl.getCurrentAuthUser ();
        Optional<T> result = getRepository ().findById (entity.getId ());
        if (result.isPresent ()) {
            if (!isValidUser (result.get (), curUser)) {
                throw new SaveNotAvailableException (entity.getId (), curUser.getId ());
            }
        }
        entity.setUser (curUser);
        entity.setCreated (result.isPresent () ? result.get ().getCreated () : LocalDateTime.now ());
        entity.setUpdated (LocalDateTime.now ());
        entity.setAccess (result.isPresent () ? result.get ().getAccess () : AccessType.PRIVATE);
        entity.setStatus (EntityStatus.ACTIVE);
    }

    private boolean isValidUser (T result, AppUserImpl user) {
        return Objects.equals (result.getUser ().getId (), user.getId ());
    }

}
