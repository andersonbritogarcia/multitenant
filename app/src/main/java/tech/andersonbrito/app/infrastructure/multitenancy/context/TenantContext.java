package tech.andersonbrito.app.infrastructure.multitenancy.context;

import org.slf4j.MDC;

public class TenantContext {

    private static final String KEY_SCHEMA = "schema";
    private static final ThreadLocal<String> tenantSchema = new ThreadLocal<>();

    public static void setTenantSchemaAdmin() {
        tenantSchema.set("admin");
        MDC.put(KEY_SCHEMA, "admin");
    }

    public static void setTenantSchema(String tenantSchema) {
        TenantContext.tenantSchema.set(tenantSchema);
        MDC.put(KEY_SCHEMA, tenantSchema);
    }

    public static String getTenantSchema() {
        return tenantSchema.get();
    }

    public static void clear() {
        tenantSchema.remove();
        MDC.remove(KEY_SCHEMA);
    }
}