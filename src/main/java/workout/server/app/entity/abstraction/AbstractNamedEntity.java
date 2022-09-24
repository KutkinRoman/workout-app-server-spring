package workout.server.app.entity.abstraction;

import workout.server.app.entity.inter.NamedEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractNamedEntity extends AbstractBaseEntity implements NamedEntity {

    @Column(nullable = false)
    private String name;

    @Override
    public String getName () {
        return name;
    }

    @Override
    public void setName (String name) {
        this.name = name;
    }
}
