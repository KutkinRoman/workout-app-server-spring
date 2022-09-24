package workout.server.app.controller.abstraction;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import workout.server.app.entity.constant.AccessType;
import workout.server.app.entity.inter.NamedEntity;
import workout.server.app.handler.AppExceptionHandler;
import workout.server.app.service.abstraction.AbstractEntityService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
public abstract class AbstractEntityController<T extends NamedEntity> extends AppExceptionHandler {

    protected abstract AbstractEntityService<T> getService ();

    @GetMapping
    public ResponseEntity<?> getAll () {
        return ResponseEntity
                .ok (getService ().getAllByCurrentAuthUserOrPublic ());
    }

    @GetMapping("/sync")
    public ResponseEntity<?> getSyncEntities (
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime latestDate
    ) {
        return ResponseEntity
                .ok (getService ().getSyncEntities (latestDate));
    }

    @PostMapping
    public ResponseEntity<?> saveByCurrentAuthUser (@RequestBody T entity, URI location) {
        return ResponseEntity
                .created (location)
                .body (getService ().saveByCurrentAuthUser (entity));
    }

    @PostMapping("/all")
    public ResponseEntity<?> saveAll (@RequestBody List<T> entities, URI location) {
        return ResponseEntity
                .created (location)
                .body (getService ().saveAllCurrentAuthUser (entities));
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PatchMapping("/{entityId}/access/{accessType}")
    public ResponseEntity<?> saveByCurrentAuthUser (@PathVariable String entityId, @PathVariable AccessType accessType) {
        return ResponseEntity
                .ok (getService ().setAccessById (accessType, entityId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete (@PathVariable String id) {
        getService ().deleteByid (id);
    }

    @DeleteMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll (@PathVariable(name = "id") List<String> ids) {
        getService ().deleteAllByIds (ids);
    }

}
