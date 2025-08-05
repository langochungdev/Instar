package com.instar.feature.auth;
import com.instar.feature.user.User;
import com.instar.feature.user.UserDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    UserDto register(User user);
//    AuthResponse refreshToken(HttpServletRequest refreshToken);
}
