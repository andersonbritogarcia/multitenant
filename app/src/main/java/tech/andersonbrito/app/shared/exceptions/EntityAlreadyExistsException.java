package tech.andersonbrito.app.shared.exceptions;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
