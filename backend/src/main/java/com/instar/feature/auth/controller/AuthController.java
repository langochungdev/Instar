package com.instar.feature.auth.controller;
import com.instar.common.exception.BusinessException;
import com.instar.common.exception.errorcode.AuthError;
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
        AuthResponse authResponse = authService.login(request);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", authResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true) // false khi dev
                .sameSite("Strict")
                .path("/")
                .maxAge(authResponse.getRefreshExpiresIn() / 1000)
                .build();

//        AuthResponse safeResponse = AuthResponse.builder()
//                .token(authResponse.getToken())
//                .expiresIn(authResponse.getExpiresIn())
//                .refreshExpiresIn(authResponse.getRefreshExpiresIn())
//                .user(authResponse.getUser())
//                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(authResponse);
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @RequestHeader(name = "X-Device-Id", required = false) String deviceId
    ) {
        authService.logout(refreshToken, deviceId);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
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


    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null) {
            throw new BusinessException(AuthError.MISSING_TOKEN, "Thiếu refresh token");
        }

        AuthResponse authResponse = authService.refresh(refreshToken);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", authResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true) // false khi dev
                .sameSite("Strict")
                .path("/")
                .maxAge(authResponse.getRefreshExpiresIn() / 1000)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(authResponse);
    }

}
