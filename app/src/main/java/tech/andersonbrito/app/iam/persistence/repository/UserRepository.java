package tech.andersonbrito.app.iam.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.andersonbrito.app.iam.persistence.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("""
            SELECT u
            FROM User u
            JOIN FETCH u.userTenants ut
            JOIN FETCH ut.tenant t
            WHERE u.email = :email
            """)
    Optional<User> findByEmail(String email);

    @Query("""
            SELECT u
            FROM User u
            JOIN FETCH u.userTenants ut
            JOIN FETCH ut.tenant t
            WHERE u.id = :id
            """)
    Optional<User> findByIdWithTenants(UUID id);

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM user_tenant ut
                WHERE ut.user_id = :userId
                AND ut.tenant_id = :tenantId
            )
            """, nativeQuery = true)
    boolean userHasTenantAccess(UUID userId, UUID tenantId);

}
