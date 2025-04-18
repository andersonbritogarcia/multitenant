package tech.andersonbrito.app.infrastructure.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.andersonbrito.app.infrastructure.multitenancy.context.TenantContext;

import java.io.IOException;

@Component
public class TenantCleanupFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}

