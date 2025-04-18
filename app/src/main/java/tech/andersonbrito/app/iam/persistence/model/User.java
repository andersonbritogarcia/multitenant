package tech.andersonbrito.app.iam.persistence.model;

import jakarta.persistence.*;
import tech.andersonbrito.app.iam.core.exception.UserDefaultTenantNotFoundException;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"user\"", schema = "admin")
public class User {

    @Id
    private UUID id;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserTenant> userTenants = new HashSet<>();

    public User() {
    }

    public User(String email, String name, String password, UserRole role, Tenant tenant) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.userTenants.add(new UserTenant(this, tenant, true));
    }

    public void associateTenant(Tenant tenant, boolean isDefault) {
        if (isDefault) {
            userTenants.forEach(UserTenant::unsetDefault);
        }
        this.userTenants.add(new UserTenant(this, tenant, isDefault));
    }

    public UUID getDefaultTenantId() {
        return userTenants.stream()
                          .filter(UserTenant::isDefaultTenant)
                          .findFirst()
                          .map(UserTenant::getTenantId)
                          .orElseThrow(UserDefaultTenantNotFoundException::new);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Set<UserTenant> getUserTenants() {
        return userTenants;
    }

    public void setUserTenants(Set<UserTenant> userTenants) {
        this.userTenants = userTenants;
    }
}
