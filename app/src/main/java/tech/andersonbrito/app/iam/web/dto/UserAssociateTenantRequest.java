package tech.andersonbrito.app.iam.web.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserAssociateTenantRequest(@NotNull UUID tenantId, boolean isDefault) {
}
