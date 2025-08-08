package com.instar.feature.auth;
import com.instar.feature.user.User;
import com.instar.feature.user.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<AuthResponse> login(AuthRequest request);
    UserDto register(User user);
//    AuthResponse refreshToken(HttpServletRequest refreshToken);
}
