package tech.andersonbrito.app.iam.core.exception;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
        super("User not found");
    }
}
