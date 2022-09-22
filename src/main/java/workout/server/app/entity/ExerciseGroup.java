package workout.server.app.entity;

import lombok.Getter;
import lombok.Setter;
import workout.server.app.entity.abstraction.AbstractLocaleEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class ExerciseGroup extends AbstractLocaleEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private ExerciseCategory category;

}
