package tech.andersonbrito.app.iam.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.andersonbrito.app.iam.persistence.model.Tenant;
import tech.andersonbrito.app.iam.persistence.repository.TenantRepository;
import tech.andersonbrito.app.shared.exceptions.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TenantServiceTest {

    @InjectMocks
    private TenantService tenantService;
    @Mock
    private TenantRepository tenantRepository;

    private final String name = "Tenant";
    private final String schema = "schema";
    private final UUID tenantId = UUID.randomUUID();

    @Test
    void shouldThrowExceptionWhenTenantAlreadyExists() {
        var existingTenant = new Tenant(name, schema);

        when(tenantRepository.findByNameOrSchemaIgnoreCase(name, schema)).thenReturn(Optional.of(existingTenant));

        var ex = assertThrows(EntityAlreadyExistsException.class, () -> tenantService.createTenant(name, schema));

        assertEquals("Tenant with name or schema already exists", ex.getMessage());
        verify(tenantRepository, never()).save(any(Tenant.class));
    }

    @Test
    void shouldCreateTenantWhenValidData() {
        when(tenantRepository.findByNameOrSchemaIgnoreCase(name, schema)).thenReturn(Optional.empty());
        when(tenantRepository.save(any(Tenant.class))).thenReturn(new Tenant(name, schema));

        var createdTenant = tenantService.createTenant(name, schema);

        assertNotNull(createdTenant);
        assertNotNull(createdTenant.getId());
        assertEquals(name, createdTenant.getName());
        assertEquals(schema, createdTenant.getSchema());
        verify(tenantRepository).save(any(Tenant.class));
    }

    @Test
    void shouldReturnEmptyWhenTenantDoesNotExist() {
        when(tenantRepository.findById(tenantId)).thenReturn(Optional.empty());

        var result = tenantService.getTenantById(tenantId);

        assertFalse(result.isPresent());
        verify(tenantRepository).findById(tenantId);
    }

    @Test
    void shouldReturnTenantWhenTenantExists() {
        Tenant tenant = new Tenant(name, schema);

        when(tenantRepository.findById(tenantId)).thenReturn(Optional.of(tenant));

        Optional<Tenant> result = tenantService.getTenantById(tenantId);

        assertTrue(result.isPresent());
        assertEquals(name, result.get().getName());
        assertEquals(schema, result.get().getSchema());
    }

    @Test
    void shouldReturnAllTenants() {
        var tenant = new Tenant(name, schema);
        var otherTenant = new Tenant("OtherTenant", "otherSchema");
        var tenants = List.of(tenant, otherTenant);
        when(tenantRepository.findAll()).thenReturn(tenants);

        var result = tenantService.getAllTenants();

        assertNotNull(result);
        assertEquals(2, result.size());
        var firstTenant = result.getFirst();
        var lastTenant = result.getLast();
        assertEquals(name, firstTenant.getName());
        assertEquals(schema, firstTenant.getSchema());
        assertEquals("OtherTenant", lastTenant.getName());
        assertEquals("otherSchema", lastTenant.getSchema());
    }
}