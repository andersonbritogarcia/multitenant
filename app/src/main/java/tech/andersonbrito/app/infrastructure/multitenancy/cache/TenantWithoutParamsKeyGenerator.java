package tech.andersonbrito.app.infrastructure.multitenancy.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.stereotype.Component;
import tech.andersonbrito.app.infrastructure.multitenancy.context.TenantContext;

import java.lang.reflect.Method;

@Component
public class TenantWithoutParamsKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return SimpleKeyGenerator.generateKey(TenantContext.getTenantSchema());
    }
}
