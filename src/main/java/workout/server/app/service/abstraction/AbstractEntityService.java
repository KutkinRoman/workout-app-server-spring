package workout.server.app.service.abstraction;

import workout.server.app.entity.constant.AccessType;
import workout.server.app.entity.inter.BaseEntity;
import workout.server.security.entity.AppUserImpl;
import workout.server.security.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.Collection;

public abstract class AbstractEntityService<T extends BaseEntity> extends AbstractSaveService<T> {

    public T getById (String id) {
        return getRepository ().getById (id);
    }

    public T setAccessById (AccessType access, String id) {
        T entity = getById (id);
        entity.setAccess (access);
        entity.setUpdated (LocalDateTime.now ());
        return getRepository ().save (entity);
    }

    public Collection<T> getAllByCurrentAuthUserOrPublic () {
        AppUserImpl user = UserServiceImpl.getCurrentAuthUser ();
        return getRepository ().findAllByUserOrAccess (user, AccessType.PUBLIC);
    }


}
