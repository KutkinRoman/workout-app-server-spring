package workout.server.app.exception;

public class SaveNotAvailableException extends RuntimeException {

    public SaveNotAvailableException (String entityId, Long userId) {
        super (
                String.format (
                        "Saving entity with id [%s] is not available to user with id [%s]",
                        entityId,
                        userId
                )
        );
    }

}
