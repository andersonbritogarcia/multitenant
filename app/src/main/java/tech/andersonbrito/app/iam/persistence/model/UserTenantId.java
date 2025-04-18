package tech.andersonbrito.app.iam.persistence.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class UserTenantId implements Serializable {

    private UUID user;
    private UUID tenant;

    public UserTenantId() {
    }

    public UserTenantId(UUID user, UUID tenant) {
        this.user = user;
        this.tenant = tenant;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof UserTenantId that)) {
            return false;
        }

        return Objects.equals(user, that.user) && Objects.equals(tenant, that.tenant);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(user);
        result = 31 * result + Objects.hashCode(tenant);
        return result;
    }
}