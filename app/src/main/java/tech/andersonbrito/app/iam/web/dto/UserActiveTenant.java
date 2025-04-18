package tech.andersonbrito.app.iam.web.dto;

import tech.andersonbrito.app.iam.persistence.model.UserTenant;

import java.util.UUID;

public record UserActiveTenant(UUID tenantId, String name) {

    public UserActiveTenant(UserTenant userTenant) {
        this(userTenant.getTenantId(), userTenant.getTenantName());
    }
}
