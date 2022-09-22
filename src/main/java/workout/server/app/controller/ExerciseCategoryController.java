package workout.server.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workout.server.app.controller.abstraction.AbstractEntityController;
import workout.server.app.entity.ExerciseCategory;
import workout.server.app.service.ExerciseCategoryService;
import workout.server.app.service.abstraction.AbstractEntityService;

@RestController
@RequestMapping("/exercises/categories")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_USER')")
public class ExerciseCategoryController extends AbstractEntityController<ExerciseCategory> {

    private final ExerciseCategoryService service;

    @Override
    protected AbstractEntityService<ExerciseCategory> getService () {
        return service;
    }
}
