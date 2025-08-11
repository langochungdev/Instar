package com.instar.feature.auth;
import com.instar.feature.user.User;
import com.instar.feature.user.UserDto;
import com.instar.feature.user.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletResponse response) {
        User e = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (e != null) {
            System.out.println("Đăng nhập: " + e.getUsername() + " - " + e.getRole());
        }
        return authService.login(request, response);
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "token", required = false) String token, HttpServletResponse response) {
        return authService.logout(token, response);
    }


    @PostMapping("/register")
    public UserDto register(@RequestBody User user) {
        return authService.register(user);
    }


    @GetMapping("/me")
    public ResponseEntity<?> checkStatus() {
        return authService.checkStatus();
    }
}
