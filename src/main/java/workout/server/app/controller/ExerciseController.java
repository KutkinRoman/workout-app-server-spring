package workout.server.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workout.server.app.controller.abstraction.AbstractEntityController;
import workout.server.app.entity.ExerciseEntity;
import workout.server.app.service.ExerciseEntityService;
import workout.server.app.service.abstraction.AbstractEntityService;

@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExerciseController extends AbstractEntityController<ExerciseEntity> {

    private final ExerciseEntityService service;

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<?> getAllByGroupId (@PathVariable String groupId) {
        return ResponseEntity
                .ok (service.getAllByGroupId (groupId));
    }

    @Override
    protected AbstractEntityService<ExerciseEntity> getService () {
        return service;
    }
}
