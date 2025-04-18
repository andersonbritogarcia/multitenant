package tech.andersonbrito.app.iam.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.andersonbrito.app.iam.persistence.model.UserRole;

import java.util.UUID;

public record UserCreateRequest(@NotBlank String email,
                                @NotBlank String displayName,
                                @NotBlank String password,
                                @NotNull UserRole role,
                                @NotNull UUID tenantId) {
}
