package tech.andersonbrito.app.iam.web.controller;

import jakarta.validation.Valid;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.andersonbrito.app.iam.core.UserService;
import tech.andersonbrito.app.iam.web.dto.LoginRequest;
import tech.andersonbrito.app.iam.web.dto.LoginResponse;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtEncoder jwtEncoder, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        var user = userService.getUserByEmail(request.email())
                              .orElseThrow(() -> new BadCredentialsException("E-mail or password is incorrect"));

        validatePassword(request.password(), user.getPassword());

        var now = Instant.now();
        var expiresIn = 7200L;
        var claims = JwtClaimsSet.builder()
                                 .subject(user.getId().toString())
                                 .issuedAt(now)
                                 .expiresAt(now.plusSeconds(expiresIn))
                                 .claim("tenant", user.getDefaultTenantId())
                                 .claim("scope", user.getRole())
                                 .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResponse(jwtValue, expiresIn);
    }

    private void validatePassword(String password, String encodedPassword) {
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new BadCredentialsException("E-mail or password is incorrect");
        }
    }
}
