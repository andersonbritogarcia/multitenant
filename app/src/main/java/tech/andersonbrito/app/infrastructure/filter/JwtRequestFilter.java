package tech.andersonbrito.app.infrastructure.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.andersonbrito.app.iam.IamApi;
import tech.andersonbrito.app.infrastructure.multitenancy.context.TenantContext;
import tech.andersonbrito.app.shared.exceptions.InternalServerException;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.UUID;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final int BEARER_TOKEN_LENGTH = 7;

    private final IamApi iamApi;
    private final JwtDecoder jwtDecoder;

    public JwtRequestFilter(IamApi iamApi, JwtDecoder jwtDecoder) {
        this.iamApi = iamApi;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var pathUrl = getPathUrl(request);
        var authorizationHeader = request.getHeader("Authorization");

        if (PublicUrls.isPublicUrl(pathUrl) || !hasBearerToken(authorizationHeader)) {
            TenantContext.setTenantSchemaAdmin();
            filterChain.doFilter(request, response);
            return;
        }

        var token = extractToken(authorizationHeader);
        configureTenantSchema(token);
        filterChain.doFilter(request, response);
    }

    private String getPathUrl(HttpServletRequest request) {
        try {
            var uri = new URI(request.getRequestURL().toString());
            return uri.getPath();
        } catch (Exception e) {
            throw new InternalServerException("Error parsing URL");
        }
    }

    private boolean hasBearerToken(String authorizationHeader) {
        return Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ");
    }

    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring(BEARER_TOKEN_LENGTH);
    }

    private void configureTenantSchema(String token) {
        var jwt = jwtDecoder.decode(token);
        var claims = jwt.getClaims();
        var userId = UUID.fromString(jwt.getSubject());
        var tenantId = UUID.fromString(claims.get("tenant").toString());
        var tenant = iamApi.getTenantById(tenantId)
                           .orElseThrow(() -> new AccessDeniedException("Tenant not found"));

        if (!iamApi.userHasTenantAccess(userId, tenantId)) {
            throw new AccessDeniedException("Tenant is not active for user");
        }

        TenantContext.setTenantSchema(tenant.schema());
    }
}