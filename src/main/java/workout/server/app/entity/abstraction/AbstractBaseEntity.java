package workout.server.app.entity.abstraction;

import org.springframework.format.annotation.DateTimeFormat;
import workout.server.app.entity.inter.BaseEntity;
import workout.server.security.entity.AppUserImpl;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractBaseEntity implements BaseEntity, Serializable {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updated;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private workout.server.app.entity.constant.AccessType access;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private workout.server.app.entity.constant.EntityStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private AppUserImpl user;

    @Override
    public String getId () {
        return id;
    }

    @Override
    public void setId (String id) {
        this.id = id;
    }

    @Override
    public LocalDateTime getCreated () {
        return created;
    }

    @Override
    public void setCreated (LocalDateTime created) {
        this.created = created;
    }

    @Override
    public LocalDateTime getUpdated () {
        return updated;
    }

    @Override
    public void setUpdated (LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    public workout.server.app.entity.constant.AccessType getAccess () {
        return access;
    }

    @Override
    public void setAccess (workout.server.app.entity.constant.AccessType access) {
        this.access = access;
    }

    @Override
    public workout.server.app.entity.constant.EntityStatus getStatus () {
        return status;
    }

    @Override
    public void setStatus (workout.server.app.entity.constant.EntityStatus status) {
        this.status = status;
    }

    @Override
    public AppUserImpl getUser () {
        return user;
    }

    @Override
    public void setUser (AppUserImpl user) {
        this.user = user;
    }
}
