package tech.andersonbrito.app.iam.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tech.andersonbrito.app.iam.core.exception.UserNotFoundException;
import tech.andersonbrito.app.iam.persistence.model.Tenant;
import tech.andersonbrito.app.iam.persistence.model.User;
import tech.andersonbrito.app.iam.persistence.model.UserRole;
import tech.andersonbrito.app.iam.persistence.repository.TenantRepository;
import tech.andersonbrito.app.iam.persistence.repository.UserRepository;
import tech.andersonbrito.app.shared.exceptions.EntityAlreadyExistsException;
import tech.andersonbrito.app.shared.exceptions.UnprocessableEntityException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TenantRepository tenantRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private final String email = "user@example.com";
    private final String name = "User";
    private final String password = "password";
    private final String encodedPassword = "encodedPassword";
    private final UUID tenantId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        var ex = assertThrows(EntityAlreadyExistsException.class,
                              () -> userService.createUser(email, name, password, UserRole.ADMIN, tenantId));

        assertEquals("User with email already exists", ex.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenTenantNotFound() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(tenantRepository.findById(tenantId)).thenReturn(Optional.empty());

        var ex = assertThrows(UnprocessableEntityException.class,
                              () -> userService.createUser(email, name, password, UserRole.ADMIN, tenantId));

        assertEquals("Tenant not found", ex.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldCreateUserWhenValidData() {
        var tenant = new Tenant("Tenant", "schema");
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(tenantRepository.findById(tenantId)).thenReturn(Optional.of(tenant));
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(new User(email, name, encodedPassword, UserRole.ADMIN, tenant));

        var user = userService.createUser(email, name, password, UserRole.ADMIN, tenantId);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(name, user.getName());
        assertEquals(encodedPassword, user.getPassword());
        assertEquals(UserRole.ADMIN, user.getRole());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldReturnEmptyWhenEmailDoesNotExist() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        var result = userService.getUserByEmail(email);

        assertFalse(result.isPresent());
        verify(userRepository).findByEmail(email);
    }

    @Test
    void shouldReturnUserWhenEmailExists() {
        var user = new User(email, name, encodedPassword, UserRole.ADMIN, new Tenant());
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        var result = userService.getUserByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
        verify(userRepository).findByEmail(email);
    }

    @Test
    void shouldReturnAllUsers() {
        var user = new User(email, name, encodedPassword, UserRole.RESIDENT, new Tenant());
        when(userRepository.findAll()).thenReturn(List.of(user));

        var result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(email, result.getFirst().getEmail());
        verify(userRepository).findAll();
    }

    @Test
    void shouldAssociateTenantWithUser() {
        var user = new User(email, name, encodedPassword, UserRole.RESIDENT, null);
        var tenant = new Tenant("Tenant", "schema");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(tenantRepository.findById(tenantId)).thenReturn(Optional.of(tenant));

        userService.associateTenant(userId, tenantId, true);

        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundForTenantAssociation() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        var ex = assertThrows(UserNotFoundException.class, () -> userService.associateTenant(userId, tenantId, true));

        assertEquals("User not found", ex.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenTenantNotFoundForTenantAssociation() {
        var user = new User(email, name, encodedPassword, UserRole.RESIDENT, null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(tenantRepository.findById(tenantId)).thenReturn(Optional.empty());

        var ex = assertThrows(UnprocessableEntityException.class, () -> userService.associateTenant(userId, tenantId, true));

        assertEquals("Tenant not found", ex.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldReturnAllActiveTenantsForUser() {
        var tenant = new Tenant("Tenant", "schema");
        var user = new User(email, name, encodedPassword, UserRole.RESIDENT, tenant);
        when(userRepository.findByIdWithTenants(userId)).thenReturn(Optional.of(user));

        var result = userService.getAllActiveTenants(userId);

        assertNotNull(result);
        assertEquals(tenant.getId(), result.getFirst().tenantId());
        assertEquals(tenant.getName(), result.getFirst().name());
        verify(userRepository).findByIdWithTenants(userId);
    }

    @Test
    void shouldCheckIfUserHasTenantAccess() {
        when(userRepository.userHasTenantAccess(userId, tenantId)).thenReturn(true);

        var result = userService.userHasTenantAccess(userId, tenantId);

        assertTrue(result);
        verify(userRepository).userHasTenantAccess(userId, tenantId);
    }
}