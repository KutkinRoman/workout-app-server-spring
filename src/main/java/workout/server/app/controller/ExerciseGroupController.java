package workout.server.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workout.server.app.controller.abstraction.AbstractEntityController;
import workout.server.app.entity.ExerciseGroup;
import workout.server.app.service.ExerciseGroupService;
import workout.server.app.service.abstraction.AbstractEntityService;

@RestController
@RequestMapping("/exercises/groups")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_USER')")
public class ExerciseGroupController extends AbstractEntityController<ExerciseGroup> {

    private final ExerciseGroupService service;

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<?> getAllByGroupId (@PathVariable String categoryId) {
        return ResponseEntity
                .ok (service.getAllByCategoryId (categoryId));
    }

    @Override
    protected AbstractEntityService<ExerciseGroup> getService () {
        return service;
    }
}
