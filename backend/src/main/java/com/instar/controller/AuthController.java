package com.instar.controller;
import com.instar.dto.AuthRequest;
import com.instar.dto.AuthResponse;
import com.instar.dto.UserDto;
import com.instar.entity.User;
import com.instar.repository.UserRepository;
import com.instar.service.UserService;
import com.instar.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = userService.login(request);
        User e = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (e != null) {
            System.out.println("Đăng nhập thành công: username = " + e.getUsername() + ", role = " + e.getRole());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        UserDto createdUser = userService.register(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // Ở đây tùy bạn lưu token ở client, backend không lưu session nên có thể làm như sau
        // Hoặc nếu lưu token blacklist thì gọi service để blacklist token
        // Ở ví dụ này chỉ giả lập trả về success
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request) {
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if (refreshToken == null || !jwtUtil.validateToken(refreshToken)) {
            return ResponseEntity.badRequest().body(null);
        }
        String userId = jwtUtil.extractUserId(refreshToken);
        UserDto user = userService.findById(Integer.valueOf(userId));
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        String newAccessToken = jwtUtil.createToken(user.getUsername(), String.valueOf(user.getId()), user.getRole());
        String newRefreshToken = jwtUtil.createRefreshToken(user.getUsername(), String.valueOf(user.getId()), user.getRole());
        long expiresIn = jwtUtil.getExpiration();

        AuthResponse authResponse = AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiresIn(expiresIn)
                .build();
        return ResponseEntity.ok(authResponse);
    }


    @GetMapping("/status")
    public ResponseEntity<?> checkStatus(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        boolean valid = token != null && jwtUtil.validateToken(token);
        if (valid) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }
}
