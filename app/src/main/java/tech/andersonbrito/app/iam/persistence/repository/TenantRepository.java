package tech.andersonbrito.app.iam.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.andersonbrito.app.iam.persistence.model.Tenant;

import java.util.Optional;
import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {

    Optional<Tenant> findByNameOrSchemaIgnoreCase(String name, String schema);
}
