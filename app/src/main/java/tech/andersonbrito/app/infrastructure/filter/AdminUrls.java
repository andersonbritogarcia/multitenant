package tech.andersonbrito.app.infrastructure.filter;

import java.util.Arrays;

public enum AdminUrls {

    TENANT("/tenants"),
    USER("/users"),
    LOGIN("/auth/login");

    private final String url;

    AdminUrls(String url) {
        this.url = url;
    }

    public static boolean isAdmin(String path) {
        var adminUrl = Arrays.stream(values()).anyMatch(admin -> path.endsWith(admin.url));
        return adminUrl || path.contains("/swagger-ui") || path.contains("api-docs");
    }
}
