package com.instar.feature.auth;
import com.instar.common.service.TokenBlacklistService;
import com.instar.common.util.CurrentUserUtil;
import com.instar.common.util.JwtUtil;
import com.instar.feature.user.User;
import com.instar.feature.user.UserDto;
import com.instar.feature.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CurrentUserUtil currentUserUtil;
    private final TokenBlacklistService tokenBlacklistService;
    AuthResponse response;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        User e = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (e != null) {
            System.out.println("Đăng nhập thành công: username = " + e.getUsername() + ", role = " + e.getRole());
        }
        return authService.login(request);
    }

    @GetMapping("/me")
    public ResponseEntity<?> checkStatus() {
        System.out.println("vao /me");
            User user = currentUserUtil.getUser();
            UserAuthDto dto = UserAuthDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();
            return ResponseEntity.ok(dto);
    }


    @PostMapping("/register")
    public UserDto register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@CookieValue(name = "token", required = false) String token, HttpServletResponse response) {
        if (token != null && jwtUtil.validateToken(token)) {
            long expiration = jwtUtil.getExpirationFromToken(token) - System.currentTimeMillis();
            tokenBlacklistService.blacklistToken(token, expiration);
        }
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok("Logout successful");
    }

}
