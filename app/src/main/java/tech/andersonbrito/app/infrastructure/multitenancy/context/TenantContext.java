package tech.andersonbrito.app.infrastructure.multitenancy.context;

import org.slf4j.MDC;

public class TenantContext {

    private static final ThreadLocal<String> tenantSchema = new ThreadLocal<>();

    public static void setTenantSchemaAdmin() {
        setTenantSchema("admin");
    }

    public static void setTenantSchema(String schema) {
        tenantSchema.set(schema);
        MDC.put("schema", schema);
    }

    public static String getTenantSchema() {
        return tenantSchema.get();
    }

    public static void clear() {
        tenantSchema.remove();
        MDC.remove("schema");
    }
}