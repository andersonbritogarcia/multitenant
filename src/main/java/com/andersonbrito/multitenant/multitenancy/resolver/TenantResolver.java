package com.andersonbrito.multitenant.multitenancy.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TenantResolver {

    private static final String TENANT_HEADER = "X-TenantId";

    @Nullable
    public String resolveTenantId(HttpServletRequest request) {
        return request.getHeader(TENANT_HEADER);
    }
}
