package workout.server.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import workout.server.app.entity.abstraction.AbstractLocaleEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class ExerciseCategory extends AbstractLocaleEntity {

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<ExerciseGroup> groups;
}
