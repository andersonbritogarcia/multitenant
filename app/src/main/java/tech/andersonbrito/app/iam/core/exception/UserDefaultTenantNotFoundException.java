package tech.andersonbrito.app.iam.core.exception;

import tech.andersonbrito.app.shared.exceptions.InternalServerException;

public class UserDefaultTenantNotFoundException extends InternalServerException {

    public UserDefaultTenantNotFoundException() {
        super("The user does not have a default tenant assigned, which is required by the application.");
    }
}
