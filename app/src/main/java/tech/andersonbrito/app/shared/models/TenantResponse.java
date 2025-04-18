package tech.andersonbrito.app.shared.models;

import java.util.UUID;

public record TenantResponse(UUID id, String name, String schema) {

}
