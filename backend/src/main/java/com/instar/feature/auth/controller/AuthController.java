package com.instar.feature.auth.controller;
import com.instar.feature.auth.dto.AuthRequest;
import com.instar.feature.auth.dto.AuthResponse;
import com.instar.feature.auth.dto.RegisterRequest;
import com.instar.feature.auth.service.AuthService;
import com.instar.feature.user.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        System.out.println(request.getUsername());
        AuthResponse authResponse = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("token", authResponse.getToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(authResponse.getExpiresIn() / 1000)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @CookieValue(name = "token", required = false) String token,
            @RequestHeader(name = "X-Device-Id", required = false) String deviceId
    ) {
        authService.logout(token, deviceId);

        ResponseCookie cookie = ResponseCookie.from("token", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(true) // bật true khi chạy HTTPS
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logout successful");
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequest request) {
        UserDto dto = authService.register(request);
        return ResponseEntity.ok(dto);
    }
}
