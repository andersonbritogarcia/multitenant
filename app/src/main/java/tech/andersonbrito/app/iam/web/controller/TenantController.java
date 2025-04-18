package tech.andersonbrito.app.iam.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.andersonbrito.app.iam.core.TenantService;
import tech.andersonbrito.app.iam.core.exception.TenantNotFoundException;
import tech.andersonbrito.app.iam.persistence.model.Tenant;
import tech.andersonbrito.app.iam.web.dto.TenantCreateRequest;
import tech.andersonbrito.app.shared.models.TenantResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TenantResponse createTenant(@RequestBody @Valid TenantCreateRequest request) {
        var tenant = tenantService.createTenant(request.name(), request.schema());
        return tenant.toResponse();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/{id}")
    TenantResponse getTenant(@PathVariable UUID id) {
        var tenant = tenantService.getTenantById(id)
                                  .orElseThrow(TenantNotFoundException::new);

        return tenant.toResponse();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping
    List<TenantResponse> getAllTenants() {
        return tenantService.getAllTenants()
                            .stream()
                            .map(Tenant::toResponse)
                            .toList();
    }
}
