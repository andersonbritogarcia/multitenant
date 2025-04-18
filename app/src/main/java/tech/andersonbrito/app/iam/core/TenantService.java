package tech.andersonbrito.app.iam.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.andersonbrito.app.iam.persistence.model.Tenant;
import tech.andersonbrito.app.iam.persistence.repository.TenantRepository;
import tech.andersonbrito.app.shared.exceptions.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TenantService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantService.class);

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Transactional
    public Tenant createTenant(String name, String schema) {
        LOGGER.info("Creating tenant with name {}, schema {}", name, schema);
        assertTenantNotExists(name, schema);
        var tenant = new Tenant(name, schema);
        return tenantRepository.save(tenant);
    }

    private void assertTenantNotExists(String name, String schema) {
        var savedTenant = tenantRepository.findByNameOrSchemaIgnoreCase(name, schema);
        if (savedTenant.isPresent()) {
            throw new EntityAlreadyExistsException("Tenant with name or schema already exists");
        }
    }

    @Transactional(readOnly = true)
    public Optional<Tenant> getTenantById(UUID id) {
        LOGGER.info("Fetching tenant with id {}", id);
        return tenantRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Tenant> getAllTenants() {
        LOGGER.info("Retrieving all tenants");
        return tenantRepository.findAll();
    }
}
