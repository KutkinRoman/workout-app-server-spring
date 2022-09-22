package workout.server.app.entity.abstraction;

import workout.server.app.entity.inter.LocaleEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractLocaleEntity extends AbstractBaseEntity implements LocaleEntity {

    @Column(nullable = false)
    private String ru;

    @Override
    public String getRu () {
        return ru;
    }

    @Override
    public void setRu (String ru) {
        this.ru = ru;
    }
}
