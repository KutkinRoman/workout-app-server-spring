package workout.server.app.service.abstraction;

import workout.server.app.entity.constant.EntityStatus;
import workout.server.app.entity.inter.BaseEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractRemovalService<T extends BaseEntity> extends AbstractSynchronizationService<T> {

    public void deleteByid (String id) {
        T entity = this.createDeletedEntityById (id);
        getRepository ().save (entity);
    }

    public void deleteAllByIds (List<String> ids) {
        Collection<T> entities = ids
                .stream ()
                .map (this::createDeletedEntityById)
                .collect (Collectors.toList ());
        getRepository ().saveAll (entities);
    }

    private T createDeletedEntityById (String id) {
        T entity = getRepository ().getById (id);
        entity.setUpdated (LocalDateTime.now ());
        entity.setStatus (EntityStatus.DELETED);
        return entity;
    }

}
