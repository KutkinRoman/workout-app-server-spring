package workout.server.app.entity;

import lombok.Getter;
import lombok.Setter;
import workout.server.app.entity.abstraction.AbstractNamedEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class ExerciseGroup extends AbstractNamedEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private ExerciseCategory category;

}
