package workout.server.app.entity.inter;

import workout.server.app.entity.constant.AccessType;
import workout.server.app.entity.constant.EntityStatus;
import workout.server.security.entity.AppUserImpl;

import java.time.LocalDateTime;

public interface BaseEntity {

    String getId ();

    void setId (String id);

    LocalDateTime getCreated ();

    void setCreated (LocalDateTime created);

    LocalDateTime getUpdated ();

    void setUpdated (LocalDateTime updated);

    AccessType getAccess ();

    void setAccess (AccessType access);

    EntityStatus getStatus ();

    void setStatus (EntityStatus status);

    AppUserImpl getUser ();

    void setUser (AppUserImpl user);

}
