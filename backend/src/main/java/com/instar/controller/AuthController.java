package com.instar.controller;
import com.instar.dto.AuthRequest;
import com.instar.dto.AuthResponse;
import com.instar.dto.UserDto;
import com.instar.entity.User;
import com.instar.repository.UserRepository;
import com.instar.service.AuthService;
import com.instar.service.UserService;
import com.instar.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthService authService;
    AuthResponse response;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        response = authService.login(request);
        User e = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (e != null) {
            System.out.println("Đăng nhập thành công: username = " + e.getUsername() + ", role = " + e.getRole());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // tạo blacklist token ko đc dùng bằng redis cache
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request) {
        try {
            response = authService.refreshToken(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
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
