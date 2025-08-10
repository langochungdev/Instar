package com.instar.feature.auth;
import com.instar.feature.user.User;
import com.instar.feature.user.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> login(AuthRequest request, HttpServletResponse response);
    UserDto register(User user);
    ResponseEntity<?> logout(String token, HttpServletResponse response);
    ResponseEntity<?> checkStatus();
}
