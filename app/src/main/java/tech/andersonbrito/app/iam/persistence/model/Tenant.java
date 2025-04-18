package tech.andersonbrito.app.iam.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import tech.andersonbrito.app.shared.models.TenantResponse;

import java.util.UUID;

@Entity
@Table(name = "tenant", schema = "admin")
public class Tenant {

    @Id
    private UUID id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String schema;

    public Tenant() {
    }

    public Tenant(String name, String schema) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.schema = schema;
    }

    public TenantResponse toResponse() {
        return new TenantResponse(this.id, this.name, this.schema);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
