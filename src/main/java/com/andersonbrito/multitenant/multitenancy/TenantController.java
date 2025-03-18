package com.andersonbrito.multitenant.multitenancy;

import com.andersonbrito.multitenant.multitenancy.context.TenantContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    @GetMapping
    String getTenant() {
        return TenantContextHolder.getTenantIdentifier();
    }
}
