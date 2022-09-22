package workout.server.app.entity;

import lombok.Getter;
import lombok.Setter;
import workout.server.app.entity.abstraction.AbstractLocaleEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
public class ExerciseEntity extends AbstractLocaleEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private ExerciseGroup group;

    @ManyToMany
    @JoinTable
    private List<Muscle> mainMuscles;

    @ManyToMany
    @JoinTable
    private List<Muscle> accessoryMuscles;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Unit unit;
}
