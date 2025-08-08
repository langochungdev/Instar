package com.instar.feature.auth;
import com.instar.common.util.CurrentUserUtil;
import com.instar.common.util.JwtUtil;
import com.instar.config.security.CustomUserDetails;
import com.instar.feature.user.User;
import com.instar.feature.user.UserDto;
import com.instar.feature.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CurrentUserUtil currentUserUtil;
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
    public ResponseEntity<String> logout(HttpServletResponse response) {
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


//    @PostMapping("/refresh")
//    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request) {
//        try {
//            response = authService.refreshToken(request);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
//
//    @GetMapping("/status")
//    public ResponseEntity<?> checkStatus(HttpServletRequest request) {
//        String token = jwtUtil.extractTokenFromRequest(request);
//        boolean valid = token != null && jwtUtil.validateToken(token);
//        if (valid) {
//            return ResponseEntity.ok("Token is valid");
//        } else {
//            return ResponseEntity.status(401).body("Invalid or expired token");
//        }
//    }
}
