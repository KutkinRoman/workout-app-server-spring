package workout.server.app.entity;

import lombok.Getter;
import lombok.Setter;
import workout.server.app.entity.abstraction.AbstractLocaleEntity;
import workout.server.app.entity.constant.MuscleType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
public class Muscle extends AbstractLocaleEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MuscleType type;

    @Column(nullable = false)
    private Integer serialNumber;

    @Column(nullable = false)
    private Integer groupNumber;
}
