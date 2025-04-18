package tech.andersonbrito.app.iam;

import org.springframework.stereotype.Component;
import tech.andersonbrito.app.iam.core.TenantService;
import tech.andersonbrito.app.iam.core.UserService;
import tech.andersonbrito.app.iam.persistence.model.Tenant;
import tech.andersonbrito.app.shared.models.TenantResponse;

import java.util.Optional;
import java.util.UUID;

@Component
public class IamApi {

    private final TenantService tenantService;
    private final UserService userService;

    public IamApi(TenantService tenantService, UserService userService) {
        this.tenantService = tenantService;
        this.userService = userService;
    }

    public Optional<TenantResponse> getTenantById(UUID id) {
        return tenantService.getTenantById(id)
                            .map(Tenant::toResponse);
    }

    public boolean userHasTenantAccess(UUID userId, UUID tenantId) {
        return userService.userHasTenantAccess(userId, tenantId);
    }
}
