package tech.andersonbrito.app.iam.core.exception;

import jakarta.persistence.EntityNotFoundException;

public class TenantNotFoundException extends EntityNotFoundException {

    public TenantNotFoundException() {
        super("Tenant not found");
    }
}
