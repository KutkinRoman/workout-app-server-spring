package workout.server.app.repository.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import workout.server.app.entity.constant.AccessType;
import workout.server.app.entity.constant.EntityStatus;
import workout.server.app.entity.inter.BaseEntity;
import workout.server.security.entity.AppUserImpl;

import java.time.LocalDateTime;
import java.util.List;


@NoRepositoryBean
public interface BaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {

    List<T> findByUserOrAccess (
            AppUserImpl v1,
            AccessType v2
    );

    List<T> findAllByUserAndStatusOrAccessAndStatus (
            AppUserImpl v1,
            EntityStatus v2,
            AccessType v3,
            EntityStatus v4
    );

    List<T> findByUserAndUpdatedAfterOrAccessAndUpdatedAfter (
            AppUserImpl v1,
            LocalDateTime v2,
            AccessType v3,
            LocalDateTime v4
    );

}
