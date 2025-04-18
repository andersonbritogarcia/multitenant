package tech.andersonbrito.app.iam.persistence.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user_tenant", schema = "admin")
@IdClass(UserTenantId.class)
public class UserTenant {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @Column(name = "default_tenant")
    private boolean defaultTenant;

    public UserTenant() {
    }

    public UserTenant(User user, Tenant tenant, boolean defaultTenant) {
        this.user = user;
        this.tenant = tenant;
        this.defaultTenant = defaultTenant;
    }

    public void unsetDefault() {
        this.defaultTenant = false;
    }

    public UUID getTenantId() {
        return tenant.getId();
    }

    public String getTenantName() {
        return tenant.getName();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public boolean isDefaultTenant() {
        return defaultTenant;
    }

    public void setDefaultTenant(boolean defaultTenant) {
        this.defaultTenant = defaultTenant;
    }
}