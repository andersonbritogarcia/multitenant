package tech.andersonbrito.app.shared.exceptions;

public class InternalServerException extends RuntimeException {

    public InternalServerException(String message) {
        super(message);
    }
}
