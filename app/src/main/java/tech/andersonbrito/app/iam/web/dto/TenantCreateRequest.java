package tech.andersonbrito.app.iam.web.dto;

import jakarta.validation.constraints.NotBlank;

public record TenantCreateRequest(@NotBlank String name, @NotBlank String schema) {
}
