package tech.andersonbrito.app.iam.web.dto;

import tech.andersonbrito.app.iam.persistence.model.User;
import tech.andersonbrito.app.iam.persistence.model.UserRole;

import java.util.UUID;

public record UserResponse(UUID id, String email, String name, UserRole role) {

    public UserResponse(User user) {
        this(user.getId(), user.getEmail(), user.getName(), user.getRole());
    }
}
