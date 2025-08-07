package com.instar.feature.auth;
import com.instar.common.util.JwtUtil;
import com.instar.feature.user.User;
import com.instar.feature.user.UserDto;
import com.instar.feature.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping("/me")
    public ResponseEntity<?> checkStatus(HttpServletRequest request) {
        String token = jwtUtil.getTokenFromCookie(request);

        if (token != null && jwtUtil.validateToken(token)) {
            String userId = jwtUtil.extractUserId(token);
            Integer id = Integer.valueOf(userId);

            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(404).body("User not found");
            }

            User user = optionalUser.get();

            UserAuthDto dto = UserAuthDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();

            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }



    @PostMapping("/register")
    public UserDto register(@RequestBody User user) {
        return authService.register(user);
    }

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletRequest request) {
//        // tạo blacklist token ko đc dùng bằng redis cache
//        return ResponseEntity.ok("Logout successful");
//    }

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
