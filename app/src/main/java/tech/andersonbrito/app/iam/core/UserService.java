package tech.andersonbrito.app.iam.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.andersonbrito.app.iam.core.exception.UserNotFoundException;
import tech.andersonbrito.app.iam.persistence.model.User;
import tech.andersonbrito.app.iam.persistence.model.UserRole;
import tech.andersonbrito.app.iam.persistence.repository.TenantRepository;
import tech.andersonbrito.app.iam.persistence.repository.UserRepository;
import tech.andersonbrito.app.iam.web.dto.UserActiveTenant;
import tech.andersonbrito.app.shared.exceptions.EntityAlreadyExistsException;
import tech.andersonbrito.app.shared.exceptions.UnprocessableEntityException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, TenantRepository tenantRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(String email, String displayName, String password, UserRole role, UUID tenantId) {
        LOGGER.info("Creating a new user with email {}", email);
        assertUserNotExists(email);
        var tenant = tenantRepository.findById(tenantId)
                                     .orElseThrow(() -> new UnprocessableEntityException("Tenant not found"));

        var user = new User(email, displayName, passwordEncoder.encode(password), role, tenant);
        return userRepository.save(user);
    }

    private void assertUserNotExists(String email) {
        var savedUser = userRepository.findByEmail(email);
        if (savedUser.isPresent()) {
            throw new EntityAlreadyExistsException("User with email already exists");
        }
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        LOGGER.info("Retrieving user with email {}", email);
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        LOGGER.info("Retrieving all users");
        return userRepository.findAll();
    }

    @Transactional
    public void associateTenant(UUID userId, UUID tenantId, boolean isDefault) {
        LOGGER.info("Associating tenant with user id {}", userId);

        var user = userRepository.findById(userId)
                                 .orElseThrow(UserNotFoundException::new);

        var tenant = tenantRepository.findById(tenantId)
                                     .orElseThrow(() -> new UnprocessableEntityException("Tenant not found"));

        user.associateTenant(tenant, isDefault);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<UserActiveTenant> getAllActiveTenants(UUID userId) {
        LOGGER.info("Getting all active tenants for user id {}", userId);
        var user = userRepository.findByIdWithTenants(userId)
                                 .orElseThrow(UserNotFoundException::new);

        return user.getUserTenants()
                   .stream()
                   .map(UserActiveTenant::new)
                   .toList();
    }

    @Transactional(readOnly = true)
    public boolean userHasTenantAccess(UUID userId, UUID tenantId) {
        LOGGER.info("Checking if user with id {} has active tenant with id {}", userId, tenantId);
        return userRepository.userHasTenantAccess(userId, tenantId);
    }
}
