package com.andersonbrito.multitenant.multitenancy.interceptor;

import com.andersonbrito.multitenant.multitenancy.context.TenantContextHolder;
import com.andersonbrito.multitenant.multitenancy.resolver.TenantResolver;
import io.micrometer.common.KeyValue;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.ServerHttpObservationFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    private final TenantResolver tenantResolver;

    public TenantInterceptor(TenantResolver tenantResolver) {
        this.tenantResolver = tenantResolver;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var tenantId = tenantResolver.resolveTenantId(request);
        TenantContextHolder.setTenantIdentifier(tenantId);
        MDC.put("tenantId", tenantId);

        ServerHttpObservationFilter
                .findObservationContext(request)
                .ifPresent(context -> context.addLowCardinalityKeyValue(KeyValue.of("tenant.id", tenantId)));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        clear();
    }

    private void clear() {
        MDC.clear();
        TenantContextHolder.clear();
    }
}
