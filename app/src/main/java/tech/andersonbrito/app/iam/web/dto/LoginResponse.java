package tech.andersonbrito.app.iam.web.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
