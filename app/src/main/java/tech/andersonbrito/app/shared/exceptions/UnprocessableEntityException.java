package tech.andersonbrito.app.shared.exceptions;

public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException(String message) {
        super(message);
    }
}
