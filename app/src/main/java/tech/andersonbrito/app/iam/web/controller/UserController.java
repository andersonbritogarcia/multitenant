package tech.andersonbrito.app.iam.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.andersonbrito.app.iam.core.UserService;
import tech.andersonbrito.app.iam.web.dto.UserActiveTenant;
import tech.andersonbrito.app.iam.web.dto.UserAssociateTenantRequest;
import tech.andersonbrito.app.iam.web.dto.UserCreateRequest;
import tech.andersonbrito.app.iam.web.dto.UserResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserResponse createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        var user = userService.createUser(userCreateRequest.email(),
                                          userCreateRequest.displayName(),
                                          userCreateRequest.password(),
                                          userCreateRequest.role(),
                                          userCreateRequest.tenantId());

        return new UserResponse(user);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping
    List<UserResponse> getAllUsers() {
        return userService.getAllUsers()
                          .stream()
                          .map(UserResponse::new)
                          .toList();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/{id}/associate-tenant")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void associateTenant(@PathVariable UUID id, @RequestBody @Valid UserAssociateTenantRequest request) {
        userService.associateTenant(id, request.tenantId(), request.isDefault());
    }

    @GetMapping("/{id}/active-tenants")
    List<UserActiveTenant> getActiveTenants(@PathVariable UUID id) {
        return userService.getAllActiveTenants(id);
    }
}
